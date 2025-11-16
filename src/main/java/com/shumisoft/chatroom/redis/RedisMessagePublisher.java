package com.shumisoft.chatroom.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.shumisoft.chatroom.dto.ChatMessageDTO;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class RedisMessagePublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChatMessageDTO message) {
        try {
            redisTemplate.convertAndSend("chatroom", message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
