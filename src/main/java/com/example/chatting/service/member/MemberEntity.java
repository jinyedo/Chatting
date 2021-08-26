package com.example.chatting.service.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "member")
@SuperBuilder
@NoArgsConstructor
@Getter @Setter
public class MemberEntity extends Member {

}
