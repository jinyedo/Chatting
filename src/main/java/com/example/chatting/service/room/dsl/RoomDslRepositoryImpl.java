package com.example.chatting.service.room.dsl;

import com.example.chatting.service.room.QRoom;
import com.example.chatting.service.room.QRoomEntity;
import com.example.chatting.service.room.RoomDTO;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.example.chatting.service.room.QRoomEntity.roomEntity;

public class RoomDslRepositoryImpl extends QuerydslRepositorySupport implements RoomDslRepository {

    public RoomDslRepositoryImpl() {
        super(QRoomEntity.class);
    }

    @Override
    public RoomDTO findByRoomId(String roomId) {
        return from(roomEntity)
                .where(
                        roomEntity.roomId.eq(roomId)
                )
                .select(Projections.bean(RoomDTO.class,
                        roomEntity.roomId,
                        roomEntity.roomName
                ))
                .fetchOne();
    }

    @Override
    public List<RoomDTO> getRoomList() {
        return from(roomEntity)
                .select(Projections.bean(RoomDTO.class,
                        roomEntity.roomId,
                        roomEntity.roomName
                ))
                .fetch();
    }
}
