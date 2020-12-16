package com.example.myrulescardgamebackend.sockets.games;

import java.util.ArrayList;

import com.corundumstudio.socketio.SocketIOClient;
import com.example.myrulescardgamebackend.sockets.Lobby;
import com.example.myrulescardgamebackend.sockets.domain.Card;

public class Player {
    String name;
    ArrayList<Card> cards;
    SocketIOClient socket;
    Game game;
    Lobby lobby;

    public Player(String name, SocketIOClient socket, Lobby lobby) {
        this.name = name;
        this.socket = socket;
        this.lobby = lobby;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getName() {
        return name;
    }

    public SocketIOClient getSocket() {
        return socket;
    }
}
