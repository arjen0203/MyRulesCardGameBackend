package com.example.myrulescardgamebackend.sockets.games.rules;

import com.example.myrulescardgamebackend.sockets.games.GameState;

public interface Rule {
    public void doRule(GameState gameState);
}
