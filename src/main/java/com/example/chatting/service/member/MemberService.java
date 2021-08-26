package com.example.chatting.service.member;

import com.example.chatting.security.MemberRole;
import com.example.chatting.security.ValidationMemberDTO;
import com.example.chatting.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService extends BaseService<String, MemberEntity, MemberDTO, MemberRepository> {

    private final MemberRepository memberRepository;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String join(ValidationMemberDTO dto) {
        Optional<MemberEntity> result =  memberRepository.findByUsernameAndFormSocial(dto.getUsername(), false);
        if (result.isPresent()) {
            return "fail";
        } else {
            MemberEntity member = MemberEntity.builder()
                        .username(dto.getUsername())
                        .password(passwordEncoder.encode(dto.getPassword()))
                        .name(dto.getName())
                        .formSocial(false)
                        .build();
            member.addMemberRole(MemberRole.USER);
            memberRepository.save(member);
        }
        return "success";
    }

    public String checkUsername(ValidationMemberDTO dto) {
        Optional<MemberEntity> result = memberRepository.findByUsernameAndFormSocial(dto.getUsername(), false);
        if (result.isPresent()) return "fail";
        return "success";
    }
}
