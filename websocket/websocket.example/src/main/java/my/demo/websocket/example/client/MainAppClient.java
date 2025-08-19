package my.demo.websocket.example.client;

public class MainAppClient {
    public static void main(String[] args) {
        WebSocketClient.MessageHandler handler = new WebSocketClient.MessageHandler() {
            @Override
            public void handleMessage(String message) {
                System.out.println("From server: " + message);
            }
        };

        try {
            WebSocketClient client = new WebSocketClient(handler);
            client.connectToServer(); // Подключаемся к серверу

            int i = 0;


            while (i < 5) { // Простое взаимодействие через консоль
                Thread.sleep(2000);
                client.sendMessage("This is message " + i); // Отправляем введенное сообщение на сервер
                i++;
            }

            client.closeConnection(); // Завершаем сессию перед выходом
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

