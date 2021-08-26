package com.example.chatting.service.room;

import com.example.chatting.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService extends BaseService<String, RoomEntity, RoomDTO, RoomRepository> {

    private final RoomRepository roomRepository;

    public List<RoomDTO> getRoomList() {
        return roomRepository.getRoomList();
    }

    public RoomDTO createRoom(RoomDTO roomDTO) {
        return saveEntity(roomDTO);
    }

    public RoomDTO findByRoomId(HashMap<Object, Object> params) {
        String roomId = (String) params.get("roomId");
        return roomRepository.findByRoomId(roomId);
    }
}
