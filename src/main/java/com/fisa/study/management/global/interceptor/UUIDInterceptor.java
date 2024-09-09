package com.fisa.study.management.global.interceptor;

import com.fisa.study.management.global.error.CustomException;
import com.fisa.study.management.global.error.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Slf4j
public class UUIDInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uuid = request.getRequestURI().split("/")[2];
        try {
            UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.ROOM_UUID_NOT_FOUND);
        }
        return true;
    }
}
