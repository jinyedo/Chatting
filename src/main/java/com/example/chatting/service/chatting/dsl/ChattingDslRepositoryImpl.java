package com.example.chatting.service.chatting.dsl;

import com.example.chatting.service.chatting.QChattingEntity;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class ChattingDslRepositoryImpl extends QuerydslRepositorySupport implements ChattingDslRepository {

    public ChattingDslRepositoryImpl() {
        super(QChattingEntity.class);
    }
}
