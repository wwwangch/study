package com.wch.study.websocket.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/7/25 13:59
 */
@RestController
public class WebsocketController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //spring提供的发送消息模板
    private SimpMessagingTemplate messagingTemplate;

    private SimpUserRegistry userRegistry;

    public WebsocketController(SimpMessagingTemplate messagingTemplate, SimpUserRegistry userRegistry) {
        this.messagingTemplate = messagingTemplate;
        this.userRegistry = userRegistry;
    }

    /*点对点通信*/
    @MessageMapping(value = "/P2P")
    public void templateTest(Principal principal) {
        logger.info("当前在线人数:" + userRegistry.getUserCount());
        int i = 1;
        for (SimpUser user : userRegistry.getUsers()) {
            logger.info("用户" + i++ + "---" + user);
        }
        //发送消息给指定用户
        messagingTemplate.convertAndSend("/topic/wch", "qqq");
        messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/wch","服务器主动推的数据");
    }

    /*广播*/
    @MessageMapping("/broadcast")
    @SendTo("/topic/getResponse")
    public String topic() throws Exception {
        return "success";
    }
}
