package com.example.myrulescardgamebackend.sockets;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.example.myrulescardgamebackend.sockets.domain.HostGame;
import com.example.myrulescardgamebackend.sockets.domain.JoinGame;
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

        server = new SocketIOServer(config);

        server.addDisconnectListener((socket) -> {
            System.out.println("A socket has left");
        });

        server.addConnectListener((socket) -> {
            System.out.println("A socket has joined");
        });

        server.addEventListener("hostGame", HostGame.class, (socket, data, ackRequest) -> {
            lobbyManager.CreateLobby(socket, data.hostName);
        });

        server.addEventListener("joinGame", JoinGame.class, (socket, data, ackRequest) -> {
            boolean succes = lobbyManager.JoinLobby(socket, data.lobbyCode, data.screenName);

            if (!succes) {
                socket.sendEvent("joinFailed");
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
                player.getSocket().sendEvent("playerJoined", lobbyData);
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

            socket.sendEvent("lobbyData", lobbyData);
        });

        server.start();
    }

}
