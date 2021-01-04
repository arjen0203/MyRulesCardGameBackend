package com.example.myrulescardgamebackend.sockets.games.rules;

import java.util.ArrayList;

import com.example.myrulescardgamebackend.sockets.domain.Card;
import com.example.myrulescardgamebackend.sockets.games.Game;
import com.example.myrulescardgamebackend.sockets.games.GameState;

public interface Rule {
    public void doRule(Game game);
    public ArrayList<Card> getCards();
}
