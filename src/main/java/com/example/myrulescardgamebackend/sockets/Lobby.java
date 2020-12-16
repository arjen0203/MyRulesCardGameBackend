package com.example.myrulescardgamebackend.sockets;

import java.util.ArrayList;
import java.util.HashMap;

import com.corundumstudio.socketio.SocketIOClient;
import com.example.myrulescardgamebackend.sockets.games.Game;
import com.example.myrulescardgamebackend.sockets.games.Player;

public class Lobby {
    String code;
    ArrayList<Player> players;
    Game game;

    Lobby(String code) {
        players = new ArrayList<>();
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
}
