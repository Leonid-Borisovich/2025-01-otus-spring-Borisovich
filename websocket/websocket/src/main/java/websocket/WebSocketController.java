package websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // Обработка входящего сообщения
    @MessageMapping("/chat/{room}")
    @SendTo("/topic/chat.{room}") // Отправляем сообщение всем подписчикам комнаты
    public String handleMessage(@Payload String message, @DestinationVariable String room) {
        return "Server received: " + message + " in room " + room;
    }

    // Пример отправки сообщения всем клиентам
    public void broadcastMessage(String message) {
        messagingTemplate.convertAndSend("/topic/global", message);
    }
}
