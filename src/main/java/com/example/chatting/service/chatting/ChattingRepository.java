package com.example.chatting.service.chatting;

import com.example.chatting.service.chatting.dsl.ChattingDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChattingRepository extends JpaRepository<ChattingEntity, String>, ChattingDslRepository {

}
