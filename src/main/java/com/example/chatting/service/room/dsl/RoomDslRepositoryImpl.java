package com.example.chatting.service.room.dsl;

import com.example.chatting.service.room.QRoomEntity;
import com.example.chatting.service.room.RoomDTO;
import com.querydsl.core.types.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<RoomDTO> getPagingList(Pageable pageable) {
        List<RoomDTO> content = from(roomEntity)
                .select(Projections.bean(RoomDTO.class,
                        roomEntity.roomId,
                        roomEntity.roomName
                ))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = from(roomEntity)
                .select(roomEntity)
                .fetchCount();

        return new PageImpl<>(content, pageable, total);
    }
}
