package com.example.myrulescardgamebackend.sockets.games;

import java.util.ArrayList;
import java.util.Collections;

import com.example.myrulescardgamebackend.sockets.domain.Card;

public class GameState {
    private ArrayList<Card> pickPile;
    private ArrayList<Card> discardPile = new ArrayList<Card>();
    private Card topCard;
    private ArrayList<Player> players;
    private ArrayList<Player> turnOrder;
    private Player currentPlayer;

    public ArrayList<Card> getPickPile() {
        return pickPile;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public ArrayList<Card> getDiscardPile() {
        return discardPile;
    }

    public Card getTopCard() {
        return topCard;
    }

    public void setTopCard(Card card) {
        this.topCard = card;
    }

    public void setPickPile(ArrayList<Card> pickPile) {
        this.pickPile = pickPile;
    }

    public ArrayList<Player> getTurnOrder() {
        return turnOrder;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }



    public void playedCard(Card card) {
        this.discardPile.add(this.topCard);
        this.topCard = card;
    }

    public void discardCard(Card card) {
        this.discardPile.add(card);
    }

    public void setTurnOrder(ArrayList<Player> turnOrder) {
        this.turnOrder = turnOrder;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void removePlayersCard(Card card, Player player) {
        player.getCards().remove(card);
    }
}
