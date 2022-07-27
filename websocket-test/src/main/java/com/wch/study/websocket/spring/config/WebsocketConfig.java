package com.wch.study.websocket.spring.config;

import com.wch.study.websocket.spring.handler.WsHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/7/25 11:14
 */
@Configuration(value = "springWebsocketConfig")
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WsHandler(), "/wch/P2P")
                .addInterceptors(new HttpSessionHandshakeInterceptor());
    }

}
