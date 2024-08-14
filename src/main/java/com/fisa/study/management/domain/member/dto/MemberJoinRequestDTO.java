package com.fisa.study.management.domain.member.dto;

import com.fisa.study.management.domain.member.entity.Member;
import com.fisa.study.management.domain.member.entity.Role;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class MemberJoinRequestDTO {

    private String username;
    private String password;

    @Builder
    public MemberJoinRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Member toEntity(PasswordEncoder passwordEncoder){
        return Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(Role.ADMIN) // 본 서비스는 ADMIN 만 로그인함
                .build();
    }
}
