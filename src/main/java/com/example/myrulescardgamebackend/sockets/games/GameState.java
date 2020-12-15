package com.example.myrulescardgamebackend.sockets.games;

import java.util.ArrayList;

import com.example.myrulescardgamebackend.sockets.domain.Card;

public class GameState {
    public ArrayList<Card> pickPile = new ArrayList<Card>();
    public ArrayList<Card> discardPile = new ArrayList<Card>();
    public Card topCard;
    public ArrayList<Player> players;
    public ArrayList<Player> turnOrder;

    public ArrayList<Card> getPickPile() {
        return pickPile;
    }

    public ArrayList<Card> getDiscardPile() {
        return discardPile;
    }

    public Card getTopCard() {
        return topCard;
    }

    public void setDiscardPile(ArrayList<Card> discardPile) {
        this.discardPile = discardPile;
    }

    public void setPickPile(ArrayList<Card> pickPile) {
        this.pickPile = pickPile;
    }

    public void setTopCard(Card topCard) {
        this.topCard = topCard;
    }

    public ArrayList<Player> getTurnOrder() {
        return turnOrder;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
