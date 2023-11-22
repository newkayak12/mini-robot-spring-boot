package com.project.robot.controller

import com.project.robot.constant.Constants
import com.project.robot.repository.dto.robot.Robot
import com.project.robot.service.RedisPublisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.Headers
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Controller

@Controller
class MessageController {

    @Autowired
    private lateinit var redisPublisher: RedisPublisher;

    @Autowired
    private lateinit var channelTopic: ChannelTopic


    @MessageMapping(value = [Constants.MESSAGE_END_POINT])
    public fun message (@Payload  robot: Robot ) {
        redisPublisher.publish(channelTopic, robot)
    }

}