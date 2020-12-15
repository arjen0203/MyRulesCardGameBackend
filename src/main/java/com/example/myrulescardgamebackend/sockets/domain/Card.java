package com.example.myrulescardgamebackend.sockets.domain;

import com.example.myrulescardgamebackend.CardEnums;
import com.example.myrulescardgamebackend.sockets.games.rules.Rule;

public class Card {
    public int suit;
    public int value;
    public Rule rule;

    public Card() {}

    public Card(int suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    public Card(CardEnums.Suit suit, CardEnums.Value value) {
        this.suit = suit.getSuit();
        this.value = value.getValue();
    }
}
