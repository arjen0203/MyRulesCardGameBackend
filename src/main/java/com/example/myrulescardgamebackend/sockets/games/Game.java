package com.example.myrulescardgamebackend.sockets.games;

import java.util.ArrayList;
import java.util.Collections;

import com.example.myrulescardgamebackend.CardEnums;
import com.example.myrulescardgamebackend.sockets.domain.Card;

public class Game {
    GameState gameState = new GameState();

    public void shufflePickPile() {
        Collections.shuffle(gameState.getPickPile());
    }

    public void setPickPile(ArrayList<Card> pickPile) {
        this.gameState.setPickPile(pickPile);
    }

    public void setPlayers(ArrayList<Player> players) {
        this.gameState.setPlayers(players);
    }

    public ArrayList<Player> getWinners() {
        ArrayList<Player> winners = new ArrayList<Player>();
        for (Player player: this.gameState.getPlayers()) {
            if (player.getCards().size() < 1) winners.add(player);
        }
        return winners;
    }
}
