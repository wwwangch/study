package com.wch.study.websocket.jakarta.config;


import io.undertow.websockets.jsr.UndertowSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.SocketAddress;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/7/27 10:45
 */
@Slf4j
@ServerEndpoint("/websocket/{userId}")
@Component
public class WebsocketBean {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    private String userId;

    private String ip;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<WebsocketBean> webSocketSet = new CopyOnWriteArraySet<WebsocketBean>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;


    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) {
        this.userId = userId;
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        //记录每个csci的所有连接   根据ip区分
        String ipBySession = getIpBySession(session);
        this.ip = ipBySession;
        log.info("{}加入连接,当前在线用户:{}", userId, onlineCount);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1

        log.info("{}退出连接,当前在线用户:{}", userId, onlineCount);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("【websocket:userId={}】接收到消息:{}", userId, message);
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebsocketBean.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebsocketBean.onlineCount--;
    }

    public static void sendMessageAll(String message) throws IOException {
        for (WebsocketBean item : webSocketSet) {
            if (item.session.isOpen()) {
                item.session.getBasicRemote().sendText(message);
            }
        }
    }

    public static Set<WebsocketBean> getAllClients() {
        return webSocketSet;
    }


    public String getUserId() {
        return this.userId;
    }

    public String getIp() {
        return this.ip;
    }

    private String getIpBySession(Session session) {
        UndertowSession undertowSession = (UndertowSession) session;
        SocketAddress peerAddress = undertowSession.getWebSocketChannel().getPeerAddress();
        String addressPort = peerAddress.toString();
        String[] split = addressPort.replace("/", "").split(":");
        if (split.length > 1) {
            return split[0];
        } else
            return "";
    }
}
