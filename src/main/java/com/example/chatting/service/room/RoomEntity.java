package com.example.chatting.service.room;

import com.example.chatting.service.member.Member;
import com.example.chatting.service.member.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "room")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class RoomEntity extends Room {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", insertable = false, updatable = false)
    private MemberEntity member;
}
