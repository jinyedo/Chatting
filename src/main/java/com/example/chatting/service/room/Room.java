package com.example.chatting.service.room;

import com.example.chatting.service.base.BaseEntity;
import com.example.chatting.service.base.DTOKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class Room extends BaseEntity {

    @Id @DTOKey("ROOM")
    protected String roomId;
    protected String roomName;
    protected String username;
}
