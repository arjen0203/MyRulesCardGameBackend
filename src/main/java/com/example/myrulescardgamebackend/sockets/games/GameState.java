package com.example.myrulescardgamebackend.sockets.games;

import java.util.ArrayList;
import java.util.Collections;

import com.example.myrulescardgamebackend.sockets.domain.Card;

public class GameState {
    public ArrayList<Card> pickPile;
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

    public Card pickCard() {
        if (this.pickPile.size() < 1) {
            this.pickPile = (ArrayList<Card>) this.discardPile.clone();
            this.discardPile.clear();
            Collections.shuffle(pickPile);
        }

        if (this.pickPile.size() < 1) {
            return null;
        }

        Card card = this.pickPile.get(pickPile.size() - 1);
        this.pickPile.remove(pickPile.size() - 1);
        return card;
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
