package com.example.chatting.handler;

import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
@Log4j2
public class WebSocketHandler extends TextWebSocketHandler {

    List<LinkedHashMap<String, Object>> sessionList = new ArrayList<>(); // 웹소켓 세션을 담아둘 리스트
    private static final String FILE_UPLOAD_PATH = "C:/websocket/";
    int fileUploadIdx = 0; // 파일을 전송한 방의 번호
    String fileUploadSession = ""; // 파일을 전송한 방의 세션 ID
    String filename = "";


    @SuppressWarnings("unchecked")
    @Override // 소켓 연결
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);

        String url = session.getUri().toString(); // ex) ws://localhost:8080/ws/chatting/1
        log.info("[웹소켓 연결] URL : " + url);
        String roomNumber = url.split("/ws/chatting/")[1]; // 연결된 방번호

        boolean flag = false;
        int idx = 0;

        if (sessionList.size() > 0) {
            for (int i=0; i<sessionList.size(); i++) {
                String rn = (String) sessionList.get(i).get("roomNumber");
                if (rn.equals(roomNumber)) {
                    flag = true;
                    idx = i;
                    break;
                }
            }
        }

        if (flag) { // 존재하는 방이라면 세션만 추가한다.
            LinkedHashMap<String, Object> map = sessionList.get(idx);
            map.put(session.getId(), session);
        } else { // 최초 생성하는 방이라면 방번호와 세션을 추가한다.
            LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
            map.put("roomNumber", roomNumber);
            map.put(session.getId(), session);
            sessionList.add(map);
        }

        // 세션등록이 끝나면 발급받은 세션 ID 값의 메시지를 발송한다.
        JSONObject obj = new JSONObject();
        obj.put("type", "getId");
        obj.put("sessionId", session.getId());
        session.sendMessage(new TextMessage(obj.toJSONString()));
    }

    // 메시지 발송
    @Override // Text 데이터가 들어오면 실행
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("----- 일반 메시지 발송 -----");
        String msg = message.getPayload(); // JSON 형태의 String 메시지를 받는다.
        JSONObject obj = jsonToObjectParse(msg); // JSON 데이터를 JSONObject 로 파싱한다.
        String rn = (String) obj.get("roomNumber"); // 방의 번호를 받는다.
        String msgType = (String) obj.get("type"); // 메시지의 타입을 확인한다.
        LinkedHashMap<String, Object> temp = new LinkedHashMap<>();
        log.info("msg : " + msg);

        if (sessionList.size() > 0) {
            for (int i=0; i<sessionList.size(); i++) {
                String roomNumber = (String) sessionList.get(i).get("roomNumber"); // 세션리스트의 저장된 방 번호를 가져와
                if (roomNumber.equals(rn)) { // 같은값이 방이 존재한다면
                    temp = sessionList.get(i); // 해당 방번호의 세션리스트의 존재하는 모든 object 값을 가져온다.
                    log.info("temp : "+ temp);
                    fileUploadIdx = i;
                    fileUploadSession = (String) obj.get("sessionId");
                    filename = obj.get("filename") != null ? (String) obj.get("filename") : null;
                    break;
                }
            }

            // 해당 방의 세션들만 찾아서 메시지를 발송해준다.
            for (String k : temp.keySet()) {
                if (k.equals("roomNumber")) continue; // 다만 방번호일 경우에는 건너뛴다.
                WebSocketSession wss = (WebSocketSession) temp.get(k);
                if (wss != null) {
                    try {
                        wss.sendMessage(new TextMessage(obj.toJSONString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        log.info("--------------------");
    }

    // 바이너리 메시지 발송
    @Override // BinaryMessage 의 데이터가 들어오면 실행
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        log.info("----- 바이너리 메시지 발송 -----");
        ByteBuffer byteBuffer = message.getPayload();
        log.info(message.getPayload());
        String fileName = filename;
        File dir = new File(FILE_UPLOAD_PATH);
        if (!dir.exists()) dir.mkdirs();

        File file = new File(FILE_UPLOAD_PATH, fileName);
        FileOutputStream out = null;
        FileChannel outChannel = null;
        try {
            byteBuffer.flip(); // byteBuffer 를 읽기 위해서 세팅
            out = new FileOutputStream(file, true); // 생성을 위해 OutputStream 을 연다.
            outChannel = out.getChannel(); // 채널을 열고
            byteBuffer.compact(); // 파일을 복사한다.
            outChannel.write(byteBuffer); // 파일을 쓴다.
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) out.close();
                if (outChannel != null) outChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        byteBuffer.position(0); // 파일을 저장하면서 position 값이 변경되었으므로 0으로 초기화한다.

        // 파일 쓰기가 끝나면 이미지를 발송한다.
        LinkedHashMap<String, Object> temp = sessionList.get(fileUploadIdx);
        for (String k : temp.keySet()) {
            if (k.equals("roomNumber")) continue;
            WebSocketSession wss = (WebSocketSession) temp.get(k);
            try {
                wss.sendMessage(new BinaryMessage(byteBuffer)); // 초기화된 버퍼를 발송하낟.
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info("--------------------");
    }

    @Override // 소켓 종료
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        if (sessionList.size() > 0) {
            for (LinkedHashMap<String, Object> stringObjectLinkedHashMapHashMap : sessionList) {
                stringObjectLinkedHashMapHashMap.remove(session.getId());
            }
        }
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
