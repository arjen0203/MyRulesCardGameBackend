package com.example.myrulescardgamebackend.sockets.domain;

import java.util.ArrayList;

public class LobbyData {
    String lobbyCode;
    ArrayList<PlayerData> players;

    public LobbyData() {

    }

    public LobbyData(String lobbyCode, ArrayList<PlayerData> players) {
        this.lobbyCode = lobbyCode;
        this.players = players;
    }
}
