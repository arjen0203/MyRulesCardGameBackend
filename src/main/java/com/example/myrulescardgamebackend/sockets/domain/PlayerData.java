package com.example.myrulescardgamebackend.sockets.domain;

import java.util.UUID;

public class PlayerData {
    public String name;
    public UUID uuid;
    public int cardAmount;
    public boolean isTurn;

    public PlayerData() {}

    public PlayerData(String name, int cardAmount, boolean isTurn) {
        this.name = name;
        this.cardAmount = cardAmount;
        this.isTurn = isTurn;
    }

    public PlayerData(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }
}
