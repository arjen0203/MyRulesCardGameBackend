package com.example.myrulescardgamebackend.sockets;

import java.util.concurrent.atomic.AtomicInteger;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.stereotype.Component;

@Component
public class SocketManager {
    SocketIOServer server;
    Configuration config;

    public SocketManager() {
        config = new Configuration();
        this.init();
    }

    public void init(){
        config.setPort(5002);
        config.setContext("/sockets");

        server = new SocketIOServer(config);

        server.addDisconnectListener((socket) -> {
            System.out.println("A socket has left");
        });

        server.addConnectListener((socket) -> {
            System.out.println("A socket has joined");
        });

        server.start();
    }

}
