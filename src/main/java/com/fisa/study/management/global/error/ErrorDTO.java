package com.fisa.study.management.global.error;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ErrorDTO {

    private String code;

    private String message;
}
