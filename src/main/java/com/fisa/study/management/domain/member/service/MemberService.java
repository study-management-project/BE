package com.fisa.study.management.domain.member.service;

import com.fisa.study.management.domain.member.dto.MemberLoginDTO;
import com.fisa.study.management.domain.member.dto.MemberRegisterDTO;
import com.fisa.study.management.domain.member.dto.MemberResponseDTO;
import com.fisa.study.management.domain.member.entity.Member;
import com.fisa.study.management.domain.member.entity.Role;
import com.fisa.study.management.domain.member.repository.MemberRepository;
import com.fisa.study.management.global.session.SessionConst;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원가입
    @Transactional
    public ResponseEntity<?> register(MemberRegisterDTO dto) {
        if (memberRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.status(432).body("이미 존재하는 이메일입니다.") ;
        }
        String encodedPassword = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()); // 비밀번호 암호화

        Member member = Member.builder()
                .username(dto.getUsername())
                .password(encodedPassword)
                .email(dto.getEmail())
                .role(Role.ADMIN)
                .build();

        memberRepository.save(member);
        return ResponseEntity.ok("성공");
    }

    public MemberResponseDTO login(MemberLoginDTO dto, HttpServletRequest request, HttpServletResponse response) {
        Optional<Member> byEmail = memberRepository.findByEmail(dto.getEmail());
        if (byEmail.isPresent()) {
            Member loginMember = byEmail.get();
            if (BCrypt.checkpw(dto.getPassword(), loginMember.getPassword())) {
                // 세션이 있으면 기존 세션반환, 없으면 생성해서 반환
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(7 * 24 * 60 * 60);
                // 세션에 로그인 회원 정보 보관
                session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember.getId());
                return entityToResponseDTO(loginMember);
            }
        }
        return null;
    }

    MemberResponseDTO entityToResponseDTO(Member member) {
        return MemberResponseDTO.builder()
                .userId(member.getId())
                .username(member.getUsername())
                .email(member.getEmail())
                .role(member.getRole())
                .build();
    }
}
