package com.hzj.wiki.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {
    //声明我这个SpringBoot应用要暴露、使用WebSocket了
    @Bean
    public ServerEndpointExporter serverEndpointExporter()  {
        return new ServerEndpointExporter();
    }
}
