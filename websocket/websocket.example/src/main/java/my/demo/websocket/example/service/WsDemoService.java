package my.demo.websocket.example.service;

import my.demo.websocket.example.client.WebSocketClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
    public class WsDemoService {

    volatile private static List<String> messages = new ArrayList<>();

    public void doDemo() {

        messages.clear();

        WebSocketClient.MessageHandler handler = new WebSocketClient.MessageHandler() {
            @Override
            public void handleMessage(String message) {
                System.out.println("From server: " + message);
                messages.add(message);
            }
        };

        try {
            WebSocketClient client = new WebSocketClient(handler);
            client.connectToServer(); // Подключаемся к серверу

            int i = 0;


            while (i < 20) { // Простое взаимодействие через консоль
                Thread.sleep(1000);
                client.sendMessage("This is message " + i); // Отправляем введенное сообщение на сервер
                i++;
            }

            client.closeConnection(); // Завершаем сессию перед выходом
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     public List<String> getMessages(){
        return messages;
    }
}
