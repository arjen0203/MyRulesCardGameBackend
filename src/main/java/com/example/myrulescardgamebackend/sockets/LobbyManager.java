package com.example.myrulescardgamebackend.sockets;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import com.corundumstudio.socketio.SocketIOClient;
import com.example.myrulescardgamebackend.sockets.games.Game;
import com.example.myrulescardgamebackend.sockets.games.Player;

public class LobbyManager {
    HashMap<String, Lobby> lobbys = new HashMap<>();
    HashMap<UUID, Player> players = new HashMap<>();
    Random random = new SecureRandom();

    public void CreateLobby(SocketIOClient socket, String hostName, Game game) {
        Lobby lobby = new Lobby(generateCode(), game);
        Player host = new Player(hostName, socket, lobby);
        lobby.setHost(host);
        lobby.addPlayer(host);
        players.put(socket.getSessionId(), host);
        lobbys.put(lobby.getCode(), lobby);
    }

    public boolean lobbyExists(String code) {
        Lobby lobby = lobbys.get(code);
        return(lobby == null);
    }

    public void joinLobby(SocketIOClient socket, String code, String playerName) {
        Lobby lobby = lobbys.get(code);
        Player player = new Player(playerName, socket, lobby);
        lobby.addPlayer(player);
        players.put(socket.getSessionId(), player);
    }

    public Lobby getLobbyBySocket(SocketIOClient socket) {
        Player player = players.get(socket.getSessionId());
        if (player == null) {
            return null;
        }
        return players.get(socket.getSessionId()).getLobby();
    }

    public Player getPlayerBySocket(SocketIOClient socket) {
        return players.get(socket.getSessionId());
    }

    public Player getPlayerByUUID(UUID uuid) {
        return players.get(uuid);
    }

    public void removePlayerBySocket(SocketIOClient socket) {
        players.remove(socket.getSessionId());
    }

    public Lobby getLobbyByCode(String code) {
        return lobbys.get(code);
    }

    public boolean nameExistsInLobby(String name, Lobby lobby) {
        for (Player player : lobby.getPlayers()) {
            if (player.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public String generateCode() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 5;

        String generatedString = random.ints(leftLimit, rightLimit + 1).filter(
                i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength).collect(StringBuilder::new,
                StringBuilder::appendCodePoint, StringBuilder::append).toString();

        generatedString = generatedString.toUpperCase();

        if (lobbys.containsKey(generatedString)) {
            return generateCode();
        }

        return generatedString;
    }

    public void removeLobby(String code) {
        lobbys.remove(code);
    }
}
