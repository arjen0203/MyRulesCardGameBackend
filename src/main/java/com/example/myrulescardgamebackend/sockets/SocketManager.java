package com.example.myrulescardgamebackend.sockets;

import java.util.ArrayList;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.example.myrulescardgamebackend.CardEnums;
import com.example.myrulescardgamebackend.sockets.domain.Card;
import com.example.myrulescardgamebackend.sockets.domain.CardData;
import com.example.myrulescardgamebackend.sockets.domain.GameStateData;
import com.example.myrulescardgamebackend.sockets.domain.HostGame;
import com.example.myrulescardgamebackend.sockets.domain.MessageData;
import com.example.myrulescardgamebackend.sockets.domain.JoinLobby;
import com.example.myrulescardgamebackend.sockets.domain.LobbyData;
import com.example.myrulescardgamebackend.sockets.domain.PlayerData;
import com.example.myrulescardgamebackend.sockets.domain.RuleAndCards;
import com.example.myrulescardgamebackend.sockets.domain.RuleSetData;
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
            RuleSet ruleSet = gameManager.createRuleSet(getDefaultRuleSet());
            Game game = gameManager.createGame(ruleSet);

            lobbyManager.CreateLobby(socket, data.hostName, game);

            socket.sendEvent("hostSucces");
            System.out.println("lobby created succes");
        });

        server.addEventListener("joinLobby", JoinLobby.class, (socket, data, ackRequest) -> {
            if (data.screenName.length() < 3 || data.screenName.length() > 20) {
                socket.sendEvent("joinFailed", new Error("name too long or too short"));
                return;
            }

            if (!lobbyManager.lobbyExists(data.code)) {
                socket.sendEvent("joinFailed", new Error("could not find a lobby with this code"));
                return;
            }

            if (lobbyManager.nameExistsInLobby(data.screenName, lobbyManager.getLobbyByCode(data.code))) {
                socket.sendEvent("joinFailed", new Error("name already exists within lobby"));
                return;
            }

            lobbyManager.joinLobby(socket, data.code, data.screenName);
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

            ArrayList<PlayerData> playersData = new ArrayList<>();
            for (Player player: game.getGameState().getTurnOrder()) {
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

        server.addEventListener("playCard", CardData.class, (socket, data, ackRequest) -> {
            Player player = lobbyManager.getPlayerBySocket(socket);
            if (player == null) return; //checks if there is a player with the socket
            Game game = player.getGame();
            if (game == null) return; //checks if there is a game linked to the player
            if (player != game.getGameState().getCurrentPlayer()) return; //checks if it is the players turn
            Card card = game.getPlayersCard(data, player);
            if (card == null) return; //check if the card exists within the players hand
            if (!game.isCardPlayable(card)) { //checks if the card can be played
                player.getSocket().sendEvent("message", new MessageData("[SERVER]: This card cannot be played", true));
                return;
            }
            game.playCard(card, player);
            ArrayList<Player> winners = game.getWinners();
            if (winners.size() > 0) {
                String winnerNames = "";
                boolean first = true;
                for (Player winner: winners) {
                    if (first) {
                        winnerNames += winner.getName();
                        first = false;
                    } else {
                        winnerNames += ", " + winner.getName();
                    }
                }
                String hasOrHave = "has";
                if (winners.size() > 1) hasOrHave = "have";
                MessageData message = new MessageData("[SERVER]: " + player.getName() + " " + hasOrHave + " won!",
                        true);
                for (Player plr: game.getGameState().getPlayers()) {
                    plr.getSocket().sendEvent("message", message);
                }
                game.getGameState().setCurrentPlayer(null);
                sendGameState(game);
                return;
            }
            game.nextPlayer();
            sendGameState(game);
        });

        server.addEventListener("pickCard", CardData.class, (socket, data, ackRequest) -> {
            Player player = lobbyManager.getPlayerBySocket(socket);
            if (player == null) return; //checks if there is a player with the socket
            Game game = player.getGame();
            if (game == null) return; //checks if there is a game linked to the player
            if (player != game.getGameState().getCurrentPlayer()) return; //checks if it is the players turn
            for (Card card: player.getCards()) {
                if (game.isCardPlayable(card)) return; //checks if the player really has no cards to play
            }
            Card card = game.pickCard();
            player.getCards().add(card);
            if (game.isCardPlayable(card)) {
                sendGameState(game);
            } else {
                MessageData message = new MessageData("[SERVER]: " + player.getName() + " could not play a card and the turn was skipped", true);
                for (Player plr: game.getGameState().getPlayers()) {
                    plr.getSocket().sendEvent("message", message);
                }
                game.nextPlayer();
                sendGameState(game);
            }
        });

        server.addEventListener("message", MessageData.class, (socket, data, ackRequest) -> {
            Player player = lobbyManager.getPlayerBySocket(socket);
            if (player == null) return; //checks if there is a player with the socket
            Game game = player.getGame();
            if (game == null) return; //checks if there is a game linked to the player

            String message = "[" + player.getName() + "]: " + data.message;
            for (Player plr: game.getGameState().getPlayers()) {
                plr.getSocket().sendEvent("message", new MessageData(message));
            }
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

    private void sendGameState(Game game) {
        ArrayList<PlayerData> playersData = new ArrayList<>();
        for (Player player: game.getGameState().getTurnOrder()) {
            playersData.add(new PlayerData(player.getName(), player.getCards().size(),
                    (game.getGameState().getCurrentPlayer() == player)));
        }

        Card currentCard = game.getGameState().getTopCard();

        for (Player player: game.getGameState().getPlayers()) {
            boolean isTurn = (player == game.getGameState().getCurrentPlayer());

            ArrayList<CardData> cards = new ArrayList<>();
            for (Card card: player.getCards()) {
                cards.add(new CardData(card));
            }

            GameStateData gameStateData = new GameStateData(playersData, isTurn, cards, currentCard);
            player.getSocket().sendEvent("gameState", gameStateData);
        }
    }

    private RuleSetData getDefaultRuleSet(){
        ArrayList<RuleAndCards> ruleAndCards = new ArrayList<>();

        ArrayList<Card> skipCards = new ArrayList<>();
        skipCards.add(new Card(CardEnums.Suit.CLUBS, CardEnums.Value.ACE));
        skipCards.add(new Card(CardEnums.Suit.HEARTHS, CardEnums.Value.ACE));
        skipCards.add(new Card(CardEnums.Suit.SPADES, CardEnums.Value.ACE));
        skipCards.add(new Card(CardEnums.Suit.DIAMONDS, CardEnums.Value.ACE));
        ruleAndCards.add(new RuleAndCards(RuleSetData.RuleEnum.SKIP, skipCards));

        ArrayList<Card> pick1Cards = new ArrayList<>();
        pick1Cards.add(new Card(CardEnums.Suit.CLUBS, CardEnums.Value.TWO));
        pick1Cards.add(new Card(CardEnums.Suit.HEARTHS, CardEnums.Value.TWO));
        pick1Cards.add(new Card(CardEnums.Suit.SPADES, CardEnums.Value.TWO));
        pick1Cards.add(new Card(CardEnums.Suit.DIAMONDS, CardEnums.Value.TWO));
        ruleAndCards.add(new RuleAndCards(RuleSetData.RuleEnum.PICK1, pick1Cards));

        ArrayList<Card> pick4Cards = new ArrayList<>();
        pick4Cards.add(new Card(CardEnums.Suit.CLUBS, CardEnums.Value.FOUR));
        pick4Cards.add(new Card(CardEnums.Suit.HEARTHS, CardEnums.Value.FOUR));
        pick4Cards.add(new Card(CardEnums.Suit.SPADES, CardEnums.Value.FOUR));
        pick4Cards.add(new Card(CardEnums.Suit.DIAMONDS, CardEnums.Value.FOUR));
        ruleAndCards.add(new RuleAndCards(RuleSetData.RuleEnum.PICK4, pick4Cards));

        ArrayList<Card> reverseCards = new ArrayList<>();
        reverseCards.add(new Card(CardEnums.Suit.CLUBS, CardEnums.Value.KING));
        reverseCards.add(new Card(CardEnums.Suit.HEARTHS, CardEnums.Value.KING));
        reverseCards.add(new Card(CardEnums.Suit.SPADES, CardEnums.Value.KING));
        reverseCards.add(new Card(CardEnums.Suit.DIAMONDS, CardEnums.Value.KING));
        ruleAndCards.add(new RuleAndCards(RuleSetData.RuleEnum.REVERSE, reverseCards));

        return new RuleSetData(ruleAndCards);
    }
}
