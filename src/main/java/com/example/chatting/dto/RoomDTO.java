package com.example.chatting.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter @Setter
@ToString
public class RoomDTO {

    private int roomNumber;
    private String roomName;
}
