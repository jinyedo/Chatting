package com.example.chatting.service.room;

import com.example.chatting.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class RoomService extends BaseService<String, RoomEntity, RoomDTO, RoomRepository> {

    private final RoomRepository roomRepository;

    public Page<RoomDTO> getPagingList(Pageable pageable) {
        return roomRepository.getPagingList(pageable);
    }

    public RoomDTO createRoom(RoomDTO roomDTO) {
        return save(roomDTO);
    }

    public RoomDTO findByRoomId(HashMap<Object, Object> params) {
        String roomId = (String) params.get("roomId");
        return roomRepository.findByRoomId(roomId);
    }
}
