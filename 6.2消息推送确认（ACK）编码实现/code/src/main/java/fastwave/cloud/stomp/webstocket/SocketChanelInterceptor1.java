package fastwave.cloud.stomp.webstocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import fastwave.cloud.stomp.controller.TextMessage;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
public class SocketChanelInterceptor1 implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
       StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

       if (StompCommand.MESSAGE.equals(accessor.getCommand())) {
           byte[] s = (byte[]) message.getPayload();
           ObjectMapper objectMapper = new ObjectMapper();

           try {
               if (Objects.nonNull(s)) {
                   TextMessage textMessage = objectMapper.readValue(s, TextMessage.class);
                   System.out.println(textMessage.getText());
                   textMessage.setText("11111111111111111");
                   return MessageBuilder.withPayload(objectMapper.writeValueAsBytes(textMessage)).copyHeaders(message.getHeaders()).build();
               }
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
       }
        return message;
    }
}
