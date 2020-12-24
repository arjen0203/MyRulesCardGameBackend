package com.example.myrulescardgamebackend.sockets;

import java.util.ArrayList;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.example.myrulescardgamebackend.sockets.domain.Card;
import com.example.myrulescardgamebackend.sockets.domain.CardData;
import com.example.myrulescardgamebackend.sockets.domain.GameStateData;
import com.example.myrulescardgamebackend.sockets.domain.HostGame;
import com.example.myrulescardgamebackend.sockets.domain.joinLobby;
import com.example.myrulescardgamebackend.sockets.domain.LobbyData;
import com.example.myrulescardgamebackend.sockets.domain.PlayerData;
import com.example.myrulescardgamebackend.sockets.games.Game;
import com.example.myrulescardgamebackend.sockets.games.GameManager;
import com.example.myrulescardgamebackend.sockets.games.Player;
import com.example.myrulescardgamebackend.sockets.games.rules.RuleSet;
import org.springframework.stereotype.Component;

@Component
public class SocketManager {
    SocketIOServer server;
    Configuration config;
    LobbyManager lobbyManager;
    GameManager gameManager;

    public SocketManager() {
        config = new Configuration();
        gameManager = new GameManager();
        lobbyManager = new LobbyManager();
        this.init();
    }

    public void init(){
        config.setPort(5002);
        config.setContext("/sockets");
        config.setHostname("0.0.0.0");

        server = new SocketIOServer(config);

        server.addDisconnectListener((socket) -> {
            Lobby lobby = lobbyManager.getLobbyBySocket(socket);

            if (lobby == null) return;

            lobby.removePlayer(lobbyManager.getPlayerBySocket(socket));

            if (lobby.getHost().getSocket() == socket) {
                for (Player player: lobby.getPlayers()) {
                    lobbyManager.removePlayerBySocket(player.getSocket());
                    player.getSocket().sendEvent("lobbyEnded");
                }

                lobbyManager.removeLobby(lobby.getCode());
            } else {
                sendLobbyDataByLobby(lobby);
            }

            lobbyManager.removePlayerBySocket(socket);
            System.out.println("A socket has left");
        });

        server.addConnectListener((socket) -> {
            System.out.println("A socket has joined: " + socket.getSessionId());
        });

        server.addEventListener("hostGame", HostGame.class, (socket, data, ackRequest) -> {
            if (data.hostName.length() < 3) return;
            //todo add global error socket

            //todo add getting the ruleSetData
            RuleSet ruleSet = gameManager.createRuleSet(null);
            Game game = gameManager.createGame(ruleSet);

            lobbyManager.CreateLobby(socket, data.hostName, game);

            socket.sendEvent("hostSucces");
            System.out.println("lobby created succes");
        });

        server.addEventListener("joinLobby", joinLobby.class, (socket, data, ackRequest) -> {
            if (data.screenName.length() < 3 || data.screenName.length() > 20) {
                socket.sendEvent("joinFailed", new Error("name too long or too short"));
                return;
            }

            if (lobbyManager.nameExistsInLobby(data.screenName, lobbyManager.getLobbyByCode(data.code))) {
                socket.sendEvent("joinFailed", new Error("name already exists within lobby"));
                return;
            }

            boolean succes = lobbyManager.JoinLobby(socket, data.code, data.screenName);

            if (!succes) {
                socket.sendEvent("joinFailed", new Error("could not find a lobby with this code"));
                return;
            }

            socket.sendEvent("lobbyJoined");

            Lobby lobby = lobbyManager.getLobbyBySocket(socket);

            sendLobbyDataByLobby(lobby);
        });

        server.addEventListener("getLobby", String.class, (socket, data, ackRequest) -> {
            Lobby lobby = lobbyManager.getLobbyBySocket(socket);

            if (lobby == null) return;

            ArrayList<PlayerData> playersData = new ArrayList<PlayerData>();
            for (Player player: lobby.getPlayers()) {
                playersData.add(new PlayerData(player.getName(), player.getSocket().getSessionId()));
            }

            LobbyData lobbyData = new LobbyData(lobby.getCode(), playersData);
            if (socket.getSessionId().equals(lobby.getHost().getSocket().getSessionId())) lobbyData.setHost(true);

            socket.sendEvent("lobbyData", lobbyData);
        });

        server.addEventListener("kickPlayer", PlayerData.class, (socket, data, ackRequest) -> {
            Lobby lobby = lobbyManager.getLobbyBySocket(socket);
            if (lobby == null) return;
            if (lobby.getHost().getSocket() != socket) return;
            Player kickPlayer = lobbyManager.getPlayerByUUID(data.uuid);
            if (kickPlayer.getSocket() == socket) return;
            lobby.removePlayer(kickPlayer);
            lobbyManager.removePlayerBySocket(kickPlayer.getSocket());
            kickPlayer.getSocket().sendEvent("kicked");

            sendLobbyDataByLobby(lobby);
        });

        server.addEventListener("startGame", String.class, (socket, data, ackRequest) -> {
            Lobby lobby = lobbyManager.getLobbyBySocket(socket);
            if (lobby == null) return;
            if (lobby.getHost().getSocket() != socket) return;

            Game game = lobby.getGame();
            gameManager.addPlayersToGame(game, lobby.getPlayers());

            game.initGame();

            for (Player player: game.getGameState().getPlayers()) {
                player.getSocket().sendEvent("gameStarted");
            }
        });

        server.addEventListener("getGameState", String.class, (socket, data, ackRequest) -> {
            if (lobbyManager.getPlayerBySocket(socket) == null) return;
            Player currentPlayer = lobbyManager.getPlayerBySocket(socket);
            if (currentPlayer.getGame() == null) return;
            Game game = currentPlayer.getGame();

            //todo move this to seperate function
            ArrayList<PlayerData> playersData = new ArrayList<>();
            for (Player player: game.getGameState().getPlayers()) {
                playersData.add(new PlayerData(player.getName(), player.getCards().size(),
                        (game.getGameState().getCurrentPlayer() == player)));
            }
            boolean isTurn = (currentPlayer == game.getGameState().getCurrentPlayer());
            ArrayList<CardData> cards = new ArrayList<>();
            for (Card card: currentPlayer.getCards()) {
                cards.add(new CardData(card));
            }
            Card currentCard = game.getGameState().getTopCard();

            GameStateData gameStateData = new GameStateData(playersData, isTurn, cards, currentCard);

            socket.sendEvent("gameState", gameStateData);
        });

        server.start();
    }

    private void sendLobbyDataByLobby(Lobby lobby) {
        ArrayList<PlayerData> playersData = new ArrayList<PlayerData>();
        for (Player player: lobby.getPlayers()) {
            playersData.add(new PlayerData(player.getName(), player.getSocket().getSessionId()));
        }
        LobbyData lobbyData = new LobbyData(lobby.getCode(), playersData);

        for (Player player: lobby.getPlayers()) {
            if (player == lobby.getHost()) lobbyData.setHost(true);

            player.getSocket().sendEvent("lobbyData", lobbyData);
        }
    }
}
