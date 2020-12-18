package com.example.myrulescardgamebackend.sockets;

import java.util.ArrayList;
import java.util.HashMap;

import com.corundumstudio.socketio.SocketIOClient;
import com.example.myrulescardgamebackend.sockets.games.Game;
import com.example.myrulescardgamebackend.sockets.games.Player;

public class Lobby {
    private String code;
    private ArrayList<Player> players;
    private Player host;
    private Game game;

    Lobby(String code) {
        players = new ArrayList<>();
        this.code = code;
    }

    public void addPlayer(Player player) {
        players.add(player);
        player.setGame(this.game);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public String getCode() {
        return code;
    }

    public void setHost(Player host) {
        this.host = host;
    }

    public Player getHost() {
        return host;
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }
}
