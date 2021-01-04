package com.example.myrulescardgamebackend.sockets.domain;

import java.util.ArrayList;

import com.example.myrulescardgamebackend.CardEnums;
import com.example.myrulescardgamebackend.sockets.games.rules.Rule;

public class Card {
    private int suit;
    private int value;
    private ArrayList<Rule> rules;

    public Card() {}

    public Card(Card card) {
        this.suit = card.getSuit();
        this.value = card.getValue();
    }

    public Card(int suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    public void addRule(Rule rule) {
        if (this.rules == null) {
            this.rules = new ArrayList<Rule>();
        }
        this.rules.add(rule);
    }

    public ArrayList<Rule> getRules() {
        return rules;
    }

    public Card(CardEnums.Suit suit, CardEnums.Value value) {
        this.suit = suit.getSuit();
        this.value = value.getValue();
    }

    public int getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }
}
