package com.project.robot.config.stomp

import com.project.robot.constant.Constants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration(value = "stomp_config")
@DependsOn(value = ["constants"])
@EnableWebSocketMessageBroker
class StompConfig: WebSocketMessageBrokerConfigurer {

    @Autowired
    private lateinit var stompHandler: StompHandler;

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint(Constants.SOCKET_END_POINT).setAllowedOriginPatterns(Constants.SOCKET_ORIGIN).withSockJS()
        registry.addEndpoint(Constants.SOCKET_END_POINT).setAllowedOriginPatterns(Constants.SOCKET_ORIGIN)
    }

    override fun configureClientInboundChannel(registration: ChannelRegistration) {
       registration.interceptors(stompHandler)
    }
}