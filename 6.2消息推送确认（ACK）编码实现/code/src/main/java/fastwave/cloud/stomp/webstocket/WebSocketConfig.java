package fastwave.cloud.stomp.webstocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


import java.util.List;
import java.util.Objects;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Autowired
    SocketChanelInterceptor socketChanelInterceptor;

    @Autowired
    SocketChanelInterceptor1 socketChanelInterceptor1;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        //客户端连接端点
        registry.addEndpoint("/websocket")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //registry.enableSimpleBroker("/topic/","/queue/");
       registry.enableStompBrokerRelay("/topic/", "/queue/", "/exchange/")
                .setRelayHost("localhost")
                .setRelayPort(61613)
                .setClientLogin("admin")
                .setClientPasscode("123456")
                        .setSystemLogin("admin")
                                .setSystemPasscode("123456");
        registry.setApplicationDestinationPrefixes("/app");
    }



    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(socketChanelInterceptor);
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.interceptors(socketChanelInterceptor1);
    }

    // 配置消息转换器
    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {

        // 将 Jackson 消息转换器添加到消息转换器列表
        messageConverters.add(new MappingJackson2MessageConverter());

        // 表示不需要进一步的配置
        return false;
    }

}
