package com.example.myrulescardgamebackend.sockets.domain;

import java.util.ArrayList;

public class GameStateData {
    public ArrayList<PlayerData> players;
    public boolean isTurn;
    public ArrayList<CardData> cardDomains;
    public SocketCard currentSocketCard;

    public GameStateData() {}

    public GameStateData(ArrayList<PlayerData> playersData, boolean isTurn, ArrayList<CardData> cardDomains,
            SocketCard currentSocketCard) {
        this.players = playersData;
        this.isTurn = isTurn;
        this.cardDomains = cardDomains;
        this.currentSocketCard = currentSocketCard;
    }
}
