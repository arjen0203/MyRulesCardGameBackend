package com.example.myrulescardgamebackend.sockets;

import java.util.ArrayList;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.example.myrulescardgamebackend.sockets.domain.HostGame;
import com.example.myrulescardgamebackend.sockets.domain.joinLobby;
import com.example.myrulescardgamebackend.sockets.domain.LobbyData;
import com.example.myrulescardgamebackend.sockets.domain.PlayerData;
import com.example.myrulescardgamebackend.sockets.games.Player;
import org.springframework.stereotype.Component;

@Component
public class SocketManager {
    SocketIOServer server;
    Configuration config;
    LobbyManager lobbyManager;

    public SocketManager() {
        config = new Configuration();
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

            lobby.removePlayer(lobbyManager.getPlayerBySocket(socket));

            if (lobby.getHost() == socket) {
                for (Player player: lobby.getPlayers()) {
                    player.getSocket().sendEvent("lobbyEnded");
                }
            }
            lobbyManager.removeLobby(lobby.getCode());

            System.out.println("A socket has left");
        });

        server.addConnectListener((socket) -> {
            System.out.println("A socket has joined: " + socket.getSessionId());
        });

        server.addEventListener("hostGame", HostGame.class, (socket, data, ackRequest) -> {
            if (data.hostName.length() < 3) return;
            //todo add global error socket

            lobbyManager.CreateLobby(socket, data.hostName);

            socket.sendEvent("hostSucces");
            System.out.println("lobby created succes");
        });

        server.addEventListener("joinLobby", joinLobby.class, (socket, data, ackRequest) -> {
            if (data.screenName.length() < 3 || data.screenName.length() > 20) {
                socket.sendEvent("joinFailed", new Error("name too long or too short"));
                return;
            }

            boolean succes = lobbyManager.JoinLobby(socket, data.code, data.screenName);

            if (!succes) {
                socket.sendEvent("joinFailed", new Error("could not find a lobby with this code"));
                return;
            }

            socket.sendEvent("lobbyJoined");

            Lobby lobby = lobbyManager.getLobbyBySocket(socket);

            ArrayList<PlayerData> playersData = new ArrayList<PlayerData>();
            for (Player player: lobby.getPlayers()) {
                playersData.add(new PlayerData(player.getName()));
            }
            LobbyData lobbyData = new LobbyData(lobby.getCode(), playersData);

            for (Player player: lobby.getPlayers()) {
                if (player == lobby.getHost()) lobbyData.setHost(true);

                player.getSocket().sendEvent("lobbyData", lobbyData);
            }
        });

        server.addEventListener("getLobby", String.class, (socket, data, ackRequest) -> {
            Lobby lobby = lobbyManager.getLobbyBySocket(socket);

            if (lobby == null) return;

            ArrayList<PlayerData> playersData = new ArrayList<PlayerData>();
            for (Player player: lobby.getPlayers()) {
                playersData.add(new PlayerData(player.getName()));
            }

            LobbyData lobbyData = new LobbyData(lobby.getCode(), playersData);
            if (socket.getSessionId().equals(lobby.getHost().getSocket().getSessionId())) lobbyData.setHost(true);

            socket.sendEvent("lobbyData", lobbyData);
        });

        server.start();
    }

}
