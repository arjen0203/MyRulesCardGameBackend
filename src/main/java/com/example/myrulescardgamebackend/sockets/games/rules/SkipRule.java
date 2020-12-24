package com.example.myrulescardgamebackend.sockets.games.rules;

import java.util.ArrayList;

import com.example.myrulescardgamebackend.sockets.domain.Card;
import com.example.myrulescardgamebackend.sockets.games.GameState;

public class SkipRule implements Rule {
    ArrayList<Card> cards;

    public SkipRule(ArrayList<Card> cards) {
        this.cards = cards;
    }

    @Override
    public void doRule(GameState gameState) {

    }

    @Override
    public ArrayList<Card> getCards() {
        return cards;
    }
}
