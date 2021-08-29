package com.example.chatting.service.chatting;

import com.example.chatting.service.member.Member;
import com.example.chatting.service.room.RoomEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "chatting")
@SuperBuilder
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class ChattingEntity extends Chatting {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", insertable = false, updatable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId", insertable = false, updatable = false)
    private RoomEntity room;

}
