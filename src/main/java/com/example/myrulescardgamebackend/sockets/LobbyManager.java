package com.example.myrulescardgamebackend.sockets;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Random;

import com.corundumstudio.socketio.SocketIOClient;
import com.example.myrulescardgamebackend.sockets.games.Player;

public class LobbyManager {
    HashMap<String, Lobby> lobbys = new HashMap<>();
    HashMap<SocketIOClient, Player> players = new HashMap<>();

    public void CreateLobby(SocketIOClient socket, String hostName) {
        Lobby lobby = new Lobby(generateCode());
        Player host = new Player(hostName, socket, lobby);
        lobby.addPlayer(host);
        players.put(socket, host);

        //todo send socket with lobby
    }

    public boolean JoinLobby(SocketIOClient socket, String lobbyCode, String playerName) {
        Lobby lobby = lobbys.get(lobbyCode);
        if (lobby == null) return false;

        Player player = new Player(playerName, socket, lobby);
        lobby.addPlayer(player);
        players.put(socket, player);
        return true;
    }

    public Lobby getLobbyBySocket(SocketIOClient socket) {
        return players.get(socket).getLobby();
    }

    public String generateCode() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        if (lobbys.containsKey(generatedString)) return generateCode();

        return generatedString;
    }
}
