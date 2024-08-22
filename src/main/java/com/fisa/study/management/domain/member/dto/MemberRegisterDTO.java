package com.fisa.study.management.domain.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberRegisterDTO {
    private String username;
    private String password;
    private String email;
}
