package fastwave.cloud.stomp.webstocket;
import com.alibaba.fastjson.JSON;
import io.netty.handler.codec.stomp.StompFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Arrays;
import java.util.Objects;

@Component
public class SocketChanelInterceptor implements ChannelInterceptor {


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        accessor.setMessageId("22222222");


        if (Objects.nonNull(accessor.getCommand()) && accessor.getCommand().equals(StompCommand.ACK)) {
            System.out.println("_______________________"+accessor.getMessage());
            System.out.println(accessor.getMessageId());
        }
        return message;
    }
}


