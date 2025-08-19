package my.demo.websocket.example.controller;

import my.demo.websocket.example.client.WebSocketClient;
import my.demo.websocket.example.service.WsDemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * http://localhost:7081/qinmescard/ws-demo
 *
 */
@RequestMapping("/ws")
@RestController
public class WsDemoController {
    private static final Logger logger = LoggerFactory.getLogger(WsDemoController.class);

    private final WsDemoService wsDemoService;

    public WsDemoController(WsDemoService wsDemoService) {
        this.wsDemoService = wsDemoService;
    }

    @GetMapping(path = "/demo")
    public String  doDemo() {
        wsDemoService.doDemo();
        return "Done";
    }

    @GetMapping(path = "/messages")
    public List<String> showAll() {
        return wsDemoService.getMessages();
    }

}
