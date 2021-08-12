package com.example.chatting.security;

import com.example.chatting.service.member.Member;
import com.example.chatting.service.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthMemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("MemberUserDetailsService loadUserByUsername : " + username);

        Optional<Member> result = memberRepository.findByUsername(username);
        if (result.isPresent()) {
            Member member = result.get();
            log.info("Member : " + member);

            return new AuthMemberDTO(
                    member.getUsername(),
                    member.getPassword(),
                    member.getRoleSet().stream().map(role ->
                            new SimpleGrantedAuthority("ROLE_" + role.name())
                    ).collect(Collectors.toSet())
            );
        }
        throw new UsernameNotFoundException("Check ID");
    }
}
