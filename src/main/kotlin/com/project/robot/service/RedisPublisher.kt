package com.project.robot.service

import com.project.robot.repository.dto.robot.Robot
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Service

@Service
@Slf4j
class RedisPublisher {

    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, *>;

    fun publish( topic: ChannelTopic, robot: Robot ) {
        redisTemplate.convertAndSend(topic.topic, robot);
    }
}