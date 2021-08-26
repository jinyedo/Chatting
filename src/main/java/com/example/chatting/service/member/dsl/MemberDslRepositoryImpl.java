package com.example.chatting.service.member.dsl;

import com.example.chatting.service.member.QMemberEntity;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class MemberDslRepositoryImpl extends QuerydslRepositorySupport implements MemberDslRepository {

    public MemberDslRepositoryImpl() {
        super(QMemberEntity.class);
    }
}
