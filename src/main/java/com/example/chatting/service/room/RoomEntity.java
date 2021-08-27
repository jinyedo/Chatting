package com.example.chatting.service.room;

import com.example.chatting.service.member.Member;
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
    @JoinColumn(name = "creator", insertable = false, updatable = false)
    private Member member;
}
