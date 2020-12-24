package com.example.myrulescardgamebackend.sockets.domain;

import java.util.ArrayList;

public class GameStateData {
    public ArrayList<PlayerData> players;
    public boolean isTurn;
    public ArrayList<CardData> cards;
    public Card currentCard;

    public GameStateData() {}

    public GameStateData(ArrayList<PlayerData> playersData, boolean isTurn, ArrayList<CardData> cards,
            Card currentCard) {
        this.players = playersData;
        this.isTurn = isTurn;
        this.cards = cards;
        this.currentCard = currentCard;
    }
}
