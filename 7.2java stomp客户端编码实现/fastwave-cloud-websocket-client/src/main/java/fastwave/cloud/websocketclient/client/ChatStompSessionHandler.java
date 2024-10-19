package fastwave.cloud.websocketclient.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class ChatStompSessionHandler extends StompSessionHandlerAdapter{

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {

        String m = headers.getMessageId();

        System.out.println( m);

        System.out.println(headers.getReceipt());
        System.out.println(headers.getReceiptId());


        System.out.println("接收订阅消息=" + (String) payload);

    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        System.out.println("+++++++++++++");
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        System.out.println("________1111");
    }
}
