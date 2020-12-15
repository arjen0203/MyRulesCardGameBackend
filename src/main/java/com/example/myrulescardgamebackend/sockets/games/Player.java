package com.example.myrulescardgamebackend.sockets.games;

import java.util.ArrayList;

import com.corundumstudio.socketio.SocketIOClient;
import com.example.myrulescardgamebackend.sockets.domain.Card;

public class Player {
    String name;
    ArrayList<Card> cards;
    SocketIOClient socket;

    public Player(String name, SocketIOClient socket) {
        this.name = name;
        this.socket = socket;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }
}
