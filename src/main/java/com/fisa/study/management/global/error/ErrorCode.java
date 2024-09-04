package com.fisa.study.management.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    INVALID_PARAMETER(BAD_REQUEST, "파라미터 값을 확인해주세요."),

    CHECKUP_NOT_FOUND(NOT_FOUND, "존재하지 않는 이해도조사 ID 입니다."),
    ROOM_NOT_FOUND(NOT_FOUND, "존재하지 않는 방 ID 입니다."),
    COMMENT_NOT_FOUND(NOT_FOUND, "존재하지 않는 메세지 ID 입니다."),
    MEMBER_NOT_FOUND(NOT_FOUND, "존재하지 않는 사용자 ID 입니다."),
    SNAPSHOT_NOT_FOUND(NOT_FOUND, "존재하지 않는 스냅샷 ID 입니다."),

    INVALID_EMAIL_FORMAT(BAD_REQUEST,"이메일 형식 잘못되었습니다."),
    EMAIL_ALREADY_EXISTS(BAD_REQUEST,"존재하는 이메일입니다."),
    INVALID_PASSWORD_FORMAT(BAD_REQUEST,"비밀번호 형식이 잘못되었습니다."),

    SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버 에러입니다. 서버 팀에 연락주세요!");

    private final HttpStatus status;
    private final String message;

}
