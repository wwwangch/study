package com.wch.study.websocket.spring.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/7/25 11:04
 */
public class WsHandler extends TextWebSocketHandler {
    private Logger logger = LoggerFactory.getLogger(WsHandler.class);

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        session.sendMessage(message);
        logger.info("websocket收到信息:{}",message.getPayload());
    }
}
