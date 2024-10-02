package com.fisa.study.management.domain.member.controller;

import com.fisa.study.management.domain.member.dto.MemberLoginDTO;
import com.fisa.study.management.domain.member.dto.MemberRegisterDTO;
import com.fisa.study.management.domain.member.dto.MemberResponseDTO;
import com.fisa.study.management.domain.member.entity.Member;
import com.fisa.study.management.domain.member.repository.MemberRepository;
import com.fisa.study.management.domain.member.service.MemberServiceImpl;
import com.fisa.study.management.global.argumentresolver.Login;
import com.fisa.study.management.global.error.CustomException;
import com.fisa.study.management.global.error.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberServiceImpl memberService;
    private final MemberRepository memberRepository;

    // login Check
    @GetMapping("/check")
    public ResponseEntity<?> loginArgumentResolver(@Login Long userId) {
        if (userId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        Optional<Member> byId = memberRepository.findById(userId);
        if (byId.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        Member member = byId.get();
        log.info("인증 성공 member = {}", member.toString());
        return ResponseEntity.ok("성공, 유저 정보 : " + member.toString());
    }

    @PostMapping("/register")
    public ResponseEntity<?> loginArgumentResolver(@RequestBody @Valid MemberRegisterDTO requestDTO, Errors errors) {
        if (errors.hasErrors()) {
            // 모든 에러를 검사
            for (ObjectError error : errors.getAllErrors()) {
                if (error instanceof FieldError fieldError) {
                    // 필드 이름과 에러 메시지 추출
                    String fieldName = fieldError.getField();
                    String errorMessage = fieldError.getDefaultMessage();

                    if ("password".equals(fieldName)) {
                        throw new CustomException(ErrorCode.INVALID_PASSWORD_FORMAT);
                    } else if ("email".equals(fieldName)) {
                        throw new CustomException(ErrorCode.INVALID_EMAIL_FORMAT);
                    } else {
                        return ResponseEntity.badRequest().body(errorMessage);
                    }
                } else {
                    return ResponseEntity.badRequest().body(error.getDefaultMessage());
                }
            }
        }
        memberService.register(requestDTO);
        return ResponseEntity.ok("성공");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginArgumentResolver(@RequestBody MemberLoginDTO requestDTO,
                                                   HttpServletRequest request, HttpServletResponse response) {
        MemberResponseDTO result = memberService.login(requestDTO, request, response);

        if (result != null) return ResponseEntity.ok("로그인 성공");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 실패");
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("로그아웃 성공");
    }
}

