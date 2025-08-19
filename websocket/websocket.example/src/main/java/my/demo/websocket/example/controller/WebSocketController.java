package my.demo.websocket.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebSocketController {
    @RequestMapping("/websocket")
    public String getWebSocket() {
        return "ws-broadcast";
   }
}
