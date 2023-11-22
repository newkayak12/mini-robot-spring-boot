package com.project.robot.config.redis

import com.project.robot.constant.Constants
import com.project.robot.service.RedisSubscriber
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration(value = "redis_config")
@DependsOn(value = ["constants"])
class RedisConfig {

    @Bean
    fun redisTemplate(connectionFactory: RedisConnectionFactory?): RedisTemplate<String, *>? {
        val redisTemplate: RedisTemplate<String, *> = RedisTemplate<String, Any>()
        redisTemplate.setConnectionFactory(connectionFactory!!)
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = GenericJackson2JsonRedisSerializer()
        return redisTemplate
    }

    @Bean
    fun redisMessageListener(connectionFactory: RedisConnectionFactory?): RedisMessageListenerContainer? {
        val container = RedisMessageListenerContainer()
        container.setConnectionFactory(connectionFactory!!)
        return container
    }

    @Bean
    fun listenerAdapter(subscriber: RedisSubscriber?): MessageListenerAdapter? {
        return MessageListenerAdapter(subscriber!!, "onMessage")
    }

    @Bean
    fun channelTopic(): ChannelTopic {
        return ChannelTopic(Constants.TOPIC)
    }
}