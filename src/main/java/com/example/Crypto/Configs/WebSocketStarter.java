package com.example.Crypto.Configs;

import com.example.Crypto.Configs.WebSocket.KrakenWebSocket;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class WebSocketStarter {

    private final KrakenWebSocket krakenWebSocket;

    public WebSocketStarter(KrakenWebSocket krakenWebSocketClient) {
        this.krakenWebSocket = krakenWebSocketClient;
    }

    @PostConstruct
    public void startWebSocket() {
        krakenWebSocket.connect();
    }

    @PreDestroy
    public void stopWebSocket() {
        krakenWebSocket.disconnect();
    }
}