package com.example.myrulescardgamebackend.sockets.domain;

import java.util.ArrayList;

public class GameStateData {
    public ArrayList<PlayerData> players;
    public boolean isTurn;
    public ArrayList<CardData> cards;
    public SocketCard currentCard;

    public GameStateData() {
    }

    public GameStateData(ArrayList<PlayerData> playersData, boolean isTurn, ArrayList<CardData> cardDomains,
            SocketCard currentSocketCard) {
        this.players = playersData;
        this.isTurn = isTurn;
        this.cards = cardDomains;
        this.currentCard = currentSocketCard;
    }
}
