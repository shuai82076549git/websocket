package fastwave.cloud.stomp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class WebsocketController {
    @Autowired
    private SimpMessagingTemplate template;


    @MessageMapping("/sendToAll")
    public String sendToAll(String msg) {
        return msg;
    }

    @MessageMapping("/send")
    @SendTo("/topic")
    public String say(String msg) {
        return msg;
    }

    @MessageMapping("/sendToUser")
    public void sendToUserByTemplate(Map<String,String> params) {
        String fromUserId = params.get("fromUserId");
        String toUserId = params.get("toUserId");
        String msg = "来自" + fromUserId + "消息:" + params.get("msg");

        String destination1 = "/queue/user_" + toUserId;
        String destination2 = "/topic/user" + toUserId;
        //template.convertAndSend(destination2, msg);
        template.convertAndSendToUser(toUserId,"/topic", msg);
        //template.convertAndSend("/topic/" + 1, msg);
    }

    //@GetMapping("/sendToUser1")
    public void sendToUser(Map<String,String> params) {
        String fromUserId = params.get("fromUserId");
        String toUserId = params.get("toUserId");
        String msg = "来自" + fromUserId + "消息:" + params.get("msg");
       // template.convertAndSendToUser(toUserId,"/topic", msg);
        template.convertAndSend("/user/1/topic", msg);
    }

    @GetMapping("/sendToAllByTemplate")
    @MessageMapping("/sendToAllByTemplate")
    public void sendToAllByTemplate(@RequestParam String msg) {
        template.convertAndSend("/topic/chat", msg);
    }

    @GetMapping("/send")
    @SendToUser("/topic/gu")
    public String msgReply(@RequestParam String msg) {
        //template.convertAndSend("/topic", msg);
        return msg;
    }

}
