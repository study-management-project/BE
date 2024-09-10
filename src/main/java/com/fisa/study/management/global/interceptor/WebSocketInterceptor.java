package com.fisa.study.management.global.interceptor;

import com.fisa.study.management.global.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Slf4j
public class WebSocketInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // Spring Security에서 인증된 사용자의 정보를 WebSocket 세션으로 전달 가능
        log.info("before handshake");
        if (request instanceof ServletServerHttpRequest) {
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            HttpSession session = servletRequest.getSession(false);
            if (session != null) {
                if (session.getAttribute(SessionConst.LOGIN_MEMBER) != null) {
                    attributes.put("sessionId", session.getId());
                    return true;
                }
            }
            log.info("추가");
            attributes.put("sessionId", "none");
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
    }
}
