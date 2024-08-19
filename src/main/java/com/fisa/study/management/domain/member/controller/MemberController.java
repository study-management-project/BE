package com.fisa.study.management.domain.member.controller;

import com.fisa.study.management.domain.member.dto.MemberLoginDTO;
import com.fisa.study.management.domain.member.dto.MemberRegisterDTO;
import com.fisa.study.management.domain.member.entity.Member;
import com.fisa.study.management.domain.member.service.MemberService;
import com.fisa.study.management.global.argumentresolver.Login;
import com.fisa.study.management.global.session.SessionConst;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // login Check
//    @GetMapping("/check")
//    public String loginArgumentResolver(
//            @Login Member loginMember) {
//        if (loginMember == null) {
//            return "인증 실패";
//        }
//        return "인증 성공 : " + loginMember.getUsername();
//    }

    @PostMapping("/register")
    public String loginArgumentResolver(@RequestBody MemberRegisterDTO requestDTO) {
        return memberService.register(requestDTO);
    }

    @PostMapping("/login")
    public String loginArgumentResolver(@RequestBody MemberLoginDTO requestDTO,
                                        HttpServletRequest request, HttpServletResponse response) {
        return memberService.login(requestDTO, request, response);
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }

        // 쿠키 삭제 (JSESSIONID 쿠키 제거)
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0); // 쿠키 즉시 만료
        response.addCookie(cookie);

        return "성공";
    }
}
