package com.example.chatting.handler;

import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;

@Component
@Log4j2
public class WebSocketHandler extends TextWebSocketHandler {

    HashMap<String, WebSocketSession> sessionHashMap = new HashMap<>(); // 웹소켓 세션을 담아둘 맵

    @Override // 소켓 연결
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        sessionHashMap.put(session.getId(), session);
        JSONObject obj = new JSONObject();
        obj.put("type", "getId");
        obj.put("sessionId", session.getId());
        session.sendMessage(new TextMessage(obj.toJSONString()));
    }

    @Override // 메시지 발송
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("----------------------------메시지 발송---------------------------");
        String msg = message.getPayload();
        log.info(msg);
        JSONObject obj = jsonToObjectParse(msg);
        for (String key : sessionHashMap.keySet()) {
            WebSocketSession wss = sessionHashMap.get(key);
            try {
                wss.sendMessage(new TextMessage(obj.toJSONString()));
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

    private static JSONObject jsonToObjectParse(String jsonStr) {
        JSONParser parser = new JSONParser();
        JSONObject obj = null;
        try {
            obj = (JSONObject) parser.parse(jsonStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
