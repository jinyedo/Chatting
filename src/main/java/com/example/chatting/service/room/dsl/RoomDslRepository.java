package com.example.chatting.service.room.dsl;

import com.example.chatting.service.room.RoomDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomDslRepository {

    RoomDTO findByRoomId(String roomId);

    Page<RoomDTO> getPagingList(Pageable pageable);
}
