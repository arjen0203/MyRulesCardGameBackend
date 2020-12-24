package com.example.myrulescardgamebackend.sockets.games;

import java.util.ArrayList;
import java.util.Collections;

import com.example.myrulescardgamebackend.CardEnums;
import com.example.myrulescardgamebackend.sockets.domain.Card;

public class Game {
    static final int CARDSPERPLAYER = 7;
    GameState gameState = new GameState();

    public void shufflePickPile() {
        Collections.shuffle(gameState.getPickPile());
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setPickPile(ArrayList<Card> pickPile) {
        this.gameState.setPickPile(pickPile);
    }

    public void setPlayers(ArrayList<Player> players) {
        this.gameState.setPlayers(players);
    }

    public void initGame() {
        //gives each player cards
        for (Player player: gameState.getPlayers()) {
            ArrayList<Card> cards = new ArrayList<>();
            for (int i = 0; i < CARDSPERPLAYER; i++) {
                cards.add(gameState.pickCard());
            }
            player.setCards(cards);
        }

        //sets turn order and current player
        ArrayList<Player> turnOrder = (ArrayList<Player>) gameState.getPlayers().clone();
        Collections.shuffle(turnOrder);
        gameState.setCurrentPlayer(gameState.getPlayers().get(0));
        gameState.playedCard(gameState.pickCard());
    }


    public ArrayList<Player> getWinners() {
        ArrayList<Player> winners = new ArrayList<Player>();
        for (Player player: this.gameState.getPlayers()) {
            if (player.getCards().size() < 1) winners.add(player);
        }
        return winners;
    }
}
