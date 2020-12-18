package com.example.myrulescardgamebackend.sockets.domain;

import java.util.ArrayList;

public class LobbyData {
    public String code;
    public ArrayList<PlayerData> players;
    public boolean isHost;

    public LobbyData() {

    }

    public LobbyData(String code, ArrayList<PlayerData> players) {
        this.code = code;
        this.players = players;
    }

    public void setHost(boolean host) {
        isHost = host;
    }
}
