package com.example.chatting.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter @Setter
public class AuthMemberDTO extends User implements OAuth2User {

    private String username;
    private String name;
    private String password;
    private String socialType;
    private boolean formSocial;
    private Map<String, Object> attr;

    public AuthMemberDTO(
            String username,
            String password,
            String name,
            boolean formSocial,
            Collection<? extends GrantedAuthority> authorities) {

        super(username, password, authorities);
        this.username = username;
        this.password = password;
        this.name = name;
        this.formSocial = formSocial;
    }

    public AuthMemberDTO(
            String username,
            String password,
            String name,
            boolean formSocial,
            Collection<? extends GrantedAuthority> authorities,
            Map<String, Object> attr) {

        this(username, password, name, formSocial, authorities);
        this.attr = attr;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }
}
