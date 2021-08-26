package com.example.chatting.service.room;

import com.example.chatting.service.room.dsl.RoomDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, String>, RoomDslRepository {

}
