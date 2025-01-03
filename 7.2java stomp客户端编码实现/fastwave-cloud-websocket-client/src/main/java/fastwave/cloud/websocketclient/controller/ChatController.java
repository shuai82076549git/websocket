package fastwave.cloud.websocketclient.controller;

import com.alibaba.fastjson.JSON;
import fastwave.cloud.websocketclient.client.ChatWebsocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ChatController {

    @Autowired
    ChatWebsocketClient chatWebsocketClient;

    @ResponseBody
    @GetMapping("/send")
    public String sendMessage()
    {
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("fromUserId", 100);
        maps.put("toUserId", "100");
        maps.put("msg", "aaa");
        String json = JSON.toJSONString(maps);
        chatWebsocketClient.sendMessage(json);

        return "发送成功";
    }
}
