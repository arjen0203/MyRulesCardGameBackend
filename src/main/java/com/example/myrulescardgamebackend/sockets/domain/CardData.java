package com.example.myrulescardgamebackend.sockets.domain;

public class CardData {
    public int suit;
    public int value;

    public CardData() {
    }

    public CardData(SocketCard socketCard) {
        this.suit = socketCard.getSuit();
        this.value = socketCard.getValue();
    }

    public int getValue() {
        return value;
    }

    public int getSuit() {
        return suit;
    }
}
