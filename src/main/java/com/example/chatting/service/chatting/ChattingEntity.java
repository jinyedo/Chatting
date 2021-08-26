package com.example.chatting.service.chatting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "chatting")
@NoArgsConstructor
@Getter @Setter
public class ChattingEntity extends Chatting{

}
