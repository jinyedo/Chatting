package com.example.chatting.service.member;

import com.example.chatting.service.base.BaseEntity;
import com.example.chatting.security.MemberRole;
import com.example.chatting.service.base.DTOKey;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@MappedSuperclass
@SuperBuilder
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class Member extends BaseEntity {

    @Id
    protected String username; // 아이디

    protected String password;

    protected String name;

    protected boolean formSocial; // 소셜 로그인 유무

    protected String socialType; // 소셜 로그인 타입

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "member_role")
    @Builder.Default
    protected Set<MemberRole> roleSet = new HashSet<>();

    public void addMemberRole(MemberRole memberRole) {
        roleSet.add(memberRole);
    }
}
