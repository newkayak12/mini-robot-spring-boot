package com.project.robot.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.project.robot.constant.Constants
import com.project.robot.repository.dto.robot.Robot
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Service


@Service
@Slf4j
class RedisSubscriber: MessageListener {

    @Autowired
    private lateinit var  redisTemplate: RedisTemplate<String, *>
    @Autowired
    private lateinit var  messagingTemplate: SimpMessageSendingOperations
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    override fun onMessage(message: Message, pattern: ByteArray?) {

        val publishMessage = redisTemplate.stringSerializer.deserialize(message.body)
        val robot: Robot = objectMapper.readValue(publishMessage, Robot::class.java)
        messagingTemplate.convertAndSend(Constants.MESSAGE_END_POINT, robot)
    }
}