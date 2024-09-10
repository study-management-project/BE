package com.fisa.study.management.domain.member.service;

import com.fisa.study.management.domain.member.dto.MemberLoginDTO;
import com.fisa.study.management.domain.member.dto.MemberRegisterDTO;
import com.fisa.study.management.domain.member.dto.MemberResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface MemberService {
    void register(MemberRegisterDTO dto);

    MemberResponseDTO login(MemberLoginDTO dto, HttpServletRequest request, HttpServletResponse response);
}
