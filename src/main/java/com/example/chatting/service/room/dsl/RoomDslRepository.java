package com.example.chatting.service.room.dsl;

import com.example.chatting.service.room.RoomDTO;

import java.util.List;

public interface RoomDslRepository {

    RoomDTO findByRoomId(String roomId);

    List<RoomDTO> getRoomList();
}
