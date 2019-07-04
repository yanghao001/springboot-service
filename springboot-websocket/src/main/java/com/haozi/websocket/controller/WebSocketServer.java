package com.haozi.websocket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author hao.yang
 * @date 2019/7/4
 */
@Slf4j
@Component
@ServerEndpoint("/websocket")
public class WebSocketServer {

    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        log.info("new connection , current user sum={}", webSocketSet.size());
        try {
            sendMessage("connect server succeed！");
        } catch (IOException e) {
            log.info("IOException", e);
        }
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        log.info("connection is lost , current user sum={}", webSocketSet.size());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("receive client msg:{}", message);
        for (WebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                log.info("IOException", e);
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.info("exception happened:{}", error.getMessage());
    }


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    public static void sendInfo(String message) throws IOException {
        for (WebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                log.info("IOException", e);
                continue;
            }
        }
    }

}
