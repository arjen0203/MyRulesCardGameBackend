package com.example.myrulescardgamebackend.sockets.games;

import java.util.ArrayList;

import com.corundumstudio.socketio.SocketIOClient;
import com.example.myrulescardgamebackend.sockets.Lobby;
import com.example.myrulescardgamebackend.sockets.domain.SocketCard;

public class Player {
    String name;
    ArrayList<SocketCard> socketCardDomains;
    SocketIOClient socket;
    Game game;
    Lobby lobby;

    public Player() {}

    public Player(String name, SocketIOClient socket, Lobby lobby) {
        this.name = name;
        this.socket = socket;
        this.lobby = lobby;
    }

    public ArrayList<SocketCard> getCards() {
        return socketCardDomains;
    }

    public void setCards(ArrayList<SocketCard> socketCardDomains) {
        this.socketCardDomains = socketCardDomains;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public String getName() {
        return name;
    }

    public SocketIOClient getSocket() {
        return socket;
    }
}
