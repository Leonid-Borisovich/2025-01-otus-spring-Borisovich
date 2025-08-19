package my.demo.websocket.example.config;

import my.demo.websocket.example.client.DemoWebSocketHandler;
import my.demo.websocket.example.handler.MyHtmlTextWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(new MyHtmlTextWebSocketHandler(), "/web-socket");


        webSocketHandlerRegistry.addHandler(new DemoWebSocketHandler(), "/demo").setAllowedOrigins("*");
    }
}
