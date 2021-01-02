package com.example.myrulescardgamebackend.sockets.domain;

public class MessageData {
    public String message;
    public boolean isServerMessage;

    public MessageData() {}

    public MessageData(String message) {
        this.message = message;
    }

    public MessageData(String message, boolean isServerMessage) {
        this.message = message;
        this.isServerMessage = isServerMessage;
    }
}
