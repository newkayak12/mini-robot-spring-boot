package com.project.robot.config.stomp

import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.stereotype.Component

@Component
@Slf4j
class StompHandler: ChannelInterceptor {

    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {



        return super.preSend(message, channel)
    }
}