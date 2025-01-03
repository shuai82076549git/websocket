package fastwave.cloud.stomp.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
public class WebsocketController {
    @Autowired
    private SimpMessagingTemplate template;

    /**
     * 2./queue/<queueName>
     *
     * @param params
     */
    @MessageMapping("/sendToUser")
    public void sendToUserByTemplate(Map<String, String> params) {
        String fromUserId = params.get("fromUserId");
        String toUserId = params.get("toUserId");
        String msg = "来自" + fromUserId + "消息:" + params.get("msg");
        String destination = "/topic/user" + toUserId;
        TextMessage textMessage = new TextMessage();
        textMessage.setText(msg);
        template.convertAndSend(destination, textMessage);
    }


    @MessageMapping("/sendToAll")
    public void sendToAll(String msg) {
        String destination = "/queue/chat";
        template.convertAndSend(destination, msg);
    }
}
