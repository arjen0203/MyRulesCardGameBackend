package com.example.myrulescardgamebackend.sockets.domain;

public class CardData {
    public int suit;
    public int value;

    public CardData() {}

    public CardData(Card card) {
        this.suit = card.getSuit();
        this.value = card.getValue();
    }
}
