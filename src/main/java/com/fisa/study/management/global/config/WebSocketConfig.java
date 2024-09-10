package com.fisa.study.management.global.config;

import com.fisa.study.management.domain.websocket.AgentWebSocketHandlerDecoratorFactory;
import com.fisa.study.management.global.interceptor.WebSocketInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Bean
    public AgentWebSocketHandlerDecoratorFactory agentWebSocketHandlerDecoratorFactory() {
        return new AgentWebSocketHandlerDecoratorFactory();
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setDecoratorFactories(agentWebSocketHandlerDecoratorFactory());
//        registration.setMessageSizeLimit(1024 * 1024); // 메시지 크기를 1MB로 설정
//        registration.setSendBufferSizeLimit(1024 * 1024); // 보낼 수 있는 최대 버퍼 크기 설정
//        registration.setSendTimeLimit(20 * 1000); // 메시지 전송 제한 시간 설정 (밀리초)
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/");
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .addInterceptors(new WebSocketInterceptor())
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
