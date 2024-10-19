package fastwave.cloud.stomp.webstocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * 功能:频道拦截器,类似管道,获取消息的一些meta数据
 */
@Component
public class SocketChanelInterceptor implements ChannelInterceptor {

    @Autowired
    private MessageChannel clientOutboundChannel;

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor.getCommand().equals(StompCommand.SEND) || accessor.getCommand().equals(StompCommand.CONNECT) || accessor.getCommand().equals(StompCommand.SUBSCRIBE)) {
            try {
                TimeUnit.SECONDS.sleep(5);
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


