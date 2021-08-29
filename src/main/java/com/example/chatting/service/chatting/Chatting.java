package com.example.chatting.service.chatting;

import com.example.chatting.service.base.BaseEntity;
import com.example.chatting.service.base.DTOKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@SuperBuilder
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class Chatting extends BaseEntity {

    @Id @DTOKey("CHAT")
    protected String chattingId;
    protected String text;
    protected String username;
    protected String roomId;

}
