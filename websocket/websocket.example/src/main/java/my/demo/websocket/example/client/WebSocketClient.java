package my.demo.websocket.example.client;


import javax.websocket.*;

import java.net.URI;
import java.net.URISyntaxException;

@ClientEndpoint
public class WebSocketClient {

    private Session userSession = null;
    private MessageHandler messageHandler;

    // Конструктор принимает обработчик сообщений
    public WebSocketClient(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }

    /**
     * Устанавливает подключение к указанному URI
     */
    public void connectToServer() throws DeploymentException, Exception {
        try {
            final WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI("ws://localhost:8080/web-socket"));
        } catch (DeploymentException | URISyntaxException e) {
            throw new DeploymentException(e.getMessage());
        }
    }

    /**
     * Отправка сообщения на сервер
     */
    public void sendMessage(String message) {
        if (this.userSession != null && this.userSession.isOpen()) {
            this.userSession.getAsyncRemote().sendText(message);
        }
    }

    /**
     * Закрытие подключения
     */
    public void closeConnection() {
        if (this.userSession != null && this.userSession.isOpen()) {
            try {
                this.userSession.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // Аннотации-обработчики событий:

    @OnOpen
    public void onOpen(Session userSession) {
        System.out.println("Connected to Server.");
        this.userSession = userSession;
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("Closing connection with reason: " + reason.getReasonPhrase());
        this.userSession = null;
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("Received from server: " + message);
        if (messageHandler != null) {
            messageHandler.handleMessage(message); // Передача сообщения внешнему обработчику
        }
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    // Интерфейс обработчика сообщений:
    public interface MessageHandler {
        void handleMessage(String message);
    }
}
