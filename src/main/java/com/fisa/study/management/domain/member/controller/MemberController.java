package com.fisa.study.management.domain.member.controller;

import com.fisa.study.management.domain.member.dto.MemberLoginDTO;
import com.fisa.study.management.domain.member.dto.MemberRegisterDTO;
import com.fisa.study.management.domain.member.entity.Member;
import com.fisa.study.management.domain.member.repository.MemberRepository;
import com.fisa.study.management.domain.member.service.MemberService;
import com.fisa.study.management.global.argumentresolver.Login;
import com.fisa.study.management.global.session.SessionConst;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    // login Check
    @GetMapping("/check")
    public String loginArgumentResolver(
            @Login Long userId) {
        if (userId == null) return "실패";
        Optional<Member> byId = memberRepository.findById(userId);
        if (byId.isEmpty()) return "존재하지 않는 사용자 입니다.";
        Member member = byId.get();
        log.info("인증 성공 member = {}", member.toString());
        return "성공, 유저 정보 : " + member.toString();
    }

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
