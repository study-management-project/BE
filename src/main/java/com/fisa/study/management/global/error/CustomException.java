package com.fisa.study.management.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@RequiredArgsConstructor
public class CustomException extends  RuntimeException{
    private final ErrorCode errorCode;
}
