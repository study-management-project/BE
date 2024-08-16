package com.fisa.study.management.domain.member.dto;

import lombok.Data;

@Data
public class MemberRegisterDTO {
    private String username;
    private String password;
    private String email;
}
