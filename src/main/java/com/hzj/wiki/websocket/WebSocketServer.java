package com.hzj.wiki.websocket;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Component
@ServerEndpoint("/ws/{token}")
public class WebSocketServer {
    //开放websocket连接

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketServer.class);

    /**
     * 每个客户端一个token
     */

    private String token = "";
    private static HashMap<String, Session> map = new HashMap<>();

    /**
     * 连接成功
     */

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token){
        map.put(token, session);
        this.token = token;
        LOG.info("有新的WebSocket连接：token:{},session id:{},当前连接数:{}", token, session.getId(), map.size());
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose(Session session) {
        map.remove(this.token);
        LOG.info("WebSocket连接关闭：token:{},session id:{},当前连接数:{}", this.token, session.getId(), map.size());
    }



    /**
     * 收到消息
     */
    @OnMessage
    public void onMessage(String message, Session session){
        LOG.info("WebSocket收到消息：{},消息内容:{}", token,  message);
    }

    /**
     * 连接错误
     */
    @OnError
    public void onError(Session session, Throwable error){
        LOG.error("WebSocket连接出错",  error);
    }

    /**
     * 群发消息
     */
    public void sendInfo(String message){
        for(String token : map.keySet()){
            Session session = map.get(token);
            try {
                session.getBasicRemote().sendText(message);
            }catch (IOException e){
                LOG.error("WebSocket推送消息失败:{},内容：{}", token, message);
            }
            LOG.info("WebSocket推送消息：{},内容：{}", token,message);
        }
    }

}
