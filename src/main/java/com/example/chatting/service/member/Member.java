package com.example.chatting.service.member;

import com.example.chatting.base.BaseEntity;
import com.example.chatting.security.MemberRole;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String username; // 아이디

    private String password;

    private String name;

    private boolean formSocial; // 소셜 로그인 유무

    private String socialType; // 소셜 로그인 타입

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    public void addMemberRole(MemberRole memberRole) {
        roleSet.add(memberRole);
    }
}
