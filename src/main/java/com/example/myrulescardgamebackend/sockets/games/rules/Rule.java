package com.example.myrulescardgamebackend.sockets.games.rules;

import java.util.ArrayList;

import com.example.myrulescardgamebackend.sockets.domain.SocketCard;
import com.example.myrulescardgamebackend.sockets.games.Game;

public interface Rule {
    public void doRule(Game game);
    public ArrayList<SocketCard> getCards();
}
