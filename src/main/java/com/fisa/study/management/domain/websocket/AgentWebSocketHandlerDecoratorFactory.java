package com.fisa.study.management.domain.websocket;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.DefaultIntroductionAdvisor;
import org.springframework.aop.target.SingletonTargetSource;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

public class AgentWebSocketHandlerDecoratorFactory implements WebSocketHandlerDecoratorFactory {

    // WebSocketHandler로 WebSocket생성할때 poxy를 만들어 여기에 intercepter로 용량을 확장시킨걸 할당시켜
    // 더많은 용량의 데이터를 보낼 수 있게 함
    @Override
    public WebSocketHandler decorate(WebSocketHandler handler) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTargetClass(AopUtils.getTargetClass(handler));
        proxyFactory.setTargetSource(new SingletonTargetSource(handler));
        proxyFactory.addAdvisor(new DefaultIntroductionAdvisor(new SubProtocolWebSocketHandlerInterceptor()));
        proxyFactory.setOptimize(true);
        proxyFactory.setExposeProxy(true);
        return (WebSocketHandler) proxyFactory.getProxy();
    }
}
