package com.haozi.websocket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
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
        sendMessage("connect server succeed！");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("receive server msg:{}", message);
        for (WebSocketServer item : webSocketSet) {
            item.sendMessage(message);
        }
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        log.info("connection is lost , current user sum={}", webSocketSet.size());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.info("exception happened:{}", error.getMessage());
    }


    public void sendMessage(String message) {
//        this.session.getBasicRemote().sendText(message);
        this.session.getAsyncRemote().sendText(message);
    }

    public void sendInfo(String message) {
        for (WebSocketServer item : webSocketSet) {
            item.sendMessage(message);
        }
    }

}
