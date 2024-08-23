package com.fisa.study.management.domain.member.dto;

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
}
