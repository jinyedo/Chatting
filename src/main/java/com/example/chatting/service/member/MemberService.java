package com.example.chatting.service.member;

import com.example.chatting.security.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String join(MemberDTO memberDTO) {
        Optional<Member> result =  memberRepository.findByUsername(memberDTO.getUsername());
        if (result.isPresent()) return "fail";
        Member member = dtoToEntity(memberDTO);
        memberRepository.save(member);
        return "success";
    }

    public String checkUsername(MemberDTO memberDTO) {
        Optional<Member> result = memberRepository.findByUsername(memberDTO.getUsername());
        if (result.isPresent()) return "fail";
        return "success";
    }

    private Member dtoToEntity(MemberDTO dto) {
        Member member = Member.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .build();
        member.addMemberRole(MemberRole.USER);
        return member;
    }

}
