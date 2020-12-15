package com.example.myrulescardgamebackend.sockets.games;

import java.util.ArrayList;
import java.util.Collections;

import com.example.myrulescardgamebackend.CardEnums;
import com.example.myrulescardgamebackend.sockets.domain.Card;

public class Game {
    GameState gameState = new GameState();

    //add rules being added
    public void initPickPile() {
        for (int suit = 0; suit < 4; suit++) {
            for (int value = 0; value < 12; value++) {
                gameState.getPickPile().add(new Card(suit, value));
            }
        }
        gameState.getPickPile().add(new Card(CardEnums.Suit.JOKER, CardEnums.Value.JOKER1));

        Collections.shuffle(gameState.getPickPile());
    }


}
