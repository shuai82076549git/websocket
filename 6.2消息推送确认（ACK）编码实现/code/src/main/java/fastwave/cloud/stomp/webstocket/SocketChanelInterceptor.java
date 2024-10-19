package fastwave.cloud.stomp.webstocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.AbstractSubscribableChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * 功能:频道拦截器,类似管道,获取消息的一些meta数据
 */
@Component
public class SocketChanelInterceptor implements ChannelInterceptor {

    @Autowired
    private MessageChannel clientOutboundChannel;

    /**
     * 实际消息发送到频道之前调用
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        return message;
    }



    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor.getCommand().equals(StompCommand.SEND) || accessor.getCommand().equals(StompCommand.CONNECT) || accessor.getCommand().equals(StompCommand.SUBSCRIBE)) {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (accessor.getReceipt() != null) {
                System.out.println(accessor.getReceipt());
                StompHeaderAccessor receipt = StompHeaderAccessor.create(StompCommand.RECEIPT);
                receipt.setReceiptId(accessor.getReceipt());
                receipt.setSessionId(accessor.getSessionId());
                clientOutboundChannel.send(MessageBuilder.createMessage(new byte[0], receipt.getMessageHeaders()));
            }
        }
    }


}


