package com.fisa.study.management.global.config;

import com.fisa.study.management.domain.websocket.AgentWebSocketHandlerDecoratorFactory;
import com.fisa.study.management.global.interceptor.WebSocketInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.HandshakeInterceptor;

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
