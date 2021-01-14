package com.example.myrulescardgamebackend.sockets.games;

import java.util.ArrayList;

import com.example.myrulescardgamebackend.sockets.domain.SocketCard;

public class GameState {
    private ArrayList<SocketCard> pickPile;
    private ArrayList<SocketCard> discardPile = new ArrayList<SocketCard>();
    private SocketCard topSocketCard;
    private ArrayList<Player> players;
    private ArrayList<Player> turnOrder;
    private Player currentPlayer;

    public ArrayList<SocketCard> getPickPile() {
        return pickPile;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public ArrayList<SocketCard> getDiscardPile() {
        return discardPile;
    }

    public SocketCard getTopCard() {
        return topSocketCard;
    }

    public void setTopCard(SocketCard socketCard) {
        this.topSocketCard = socketCard;
    }

    public void setPickPile(ArrayList<SocketCard> pickPile) {
        this.pickPile = pickPile;
    }

    public ArrayList<Player> getTurnOrder() {
        return turnOrder;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void playedCard(SocketCard socketCard) {
        this.discardPile.add(this.topSocketCard);
        this.topSocketCard = socketCard;
    }

    public void discardCard(SocketCard socketCard) {
        this.discardPile.add(socketCard);
    }

    public void setTurnOrder(ArrayList<Player> turnOrder) {
        this.turnOrder = turnOrder;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void removePlayersCard(SocketCard socketCard, Player player) {
        player.getCards().remove(socketCard);
    }
}
