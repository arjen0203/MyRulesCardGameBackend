package com.example.myrulescardgamebackend.sockets.domain;

import java.util.UUID;

public class PlayerData {
    public String name;
    public UUID uuid;

    public PlayerData() {}

    public PlayerData(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }
}
