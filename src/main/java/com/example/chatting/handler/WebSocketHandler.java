package com.example.chatting.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    HashMap<String, WebSocketSession> sessionHashMap = new HashMap<>(); // 웹소켓 세션을 담아둘 맵

    @Override // 소켓 연결
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        sessionHashMap.put(session.getId(), session);
    }

    @Override // 메시지 발송
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String msg = message.getPayload();
        for (String key : sessionHashMap.keySet()) {
            WebSocketSession wss = sessionHashMap.get(key);
            try {
                wss.sendMessage(new TextMessage(msg));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // 소켓 종료
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionHashMap.remove(session.getId());
        super.afterConnectionClosed(session, status);
    }
}
