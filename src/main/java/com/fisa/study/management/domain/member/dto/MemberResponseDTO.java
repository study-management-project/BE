package com.fisa.study.management.domain.member.dto;

import com.fisa.study.management.domain.member.entity.Member;
import com.fisa.study.management.domain.member.entity.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberResponseDTO {
    private Long userId;
    private String username;
    private String email;
    private Role role;

    public static MemberResponseDTO from(Member member) {
        return MemberResponseDTO.builder()
                .userId(member.getId())
                .username(member.getUsername())
                .email(member.getEmail())
                .role(member.getRole())
                .build();
    }
}
