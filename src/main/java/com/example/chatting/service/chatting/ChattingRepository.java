package com.example.chatting.service.chatting;

import com.example.chatting.service.room.RoomDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ChattingRepository {
    List<RoomDTO> roomList = new ArrayList<>();
    int roomNumber = 0;

    // 방 생성 후 방 리스트 리턴
    public List<RoomDTO> createRoom(HashMap<Object, Object> params) {
        String roomName = (String) params.get("roomName");
        if (roomName != null && !roomName.trim().equals("")) {
            RoomDTO roomDTO = RoomDTO.builder()
                    .roomName(roomName)
                    .roomNumber(++roomNumber)
                    .build();
            roomList.add(roomDTO);
        }
        return roomList;
    }

    // 방 전체 리스트 리턴
    public List<RoomDTO> findAll() {
        return roomList;
    }

    // 방번호로 방 찾기
    public List<RoomDTO> findByRoomNumber(HashMap<Object, Object> params) {
        int roomNumber = Integer.parseInt((String) params.get("roomNumber"));
        return roomList.stream().filter(o -> o.getRoomNumber() == roomNumber).collect(Collectors.toList());
    }
}
