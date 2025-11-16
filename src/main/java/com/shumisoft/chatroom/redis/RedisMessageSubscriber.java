package com.shumisoft.chatroom.redis;

import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.Message;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shumisoft.chatroom.dto.ChatMessageDTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Component
@AllArgsConstructor
public class RedisMessageSubscriber implements MessageListener {

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String json = new String(message.getBody());
            ChatMessageDTO chatMessage = new ObjectMapper().readValue(json, ChatMessageDTO.class);

            messagingTemplate.convertAndSend("/topic/public", chatMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
