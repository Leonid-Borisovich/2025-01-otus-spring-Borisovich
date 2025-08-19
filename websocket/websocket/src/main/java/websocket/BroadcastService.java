package websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BroadcastService {

    @Autowired
    private WebSocketController controller;

    // Периодическая задача, выполняющаяся каждые 5 секунд
    @Scheduled(fixedRate = 5000)
    public void broadcastMessage() {
        controller.broadcastMessage("Broadcast at " + System.currentTimeMillis());
    }
}
