package com.shumisoft.chatroom.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.shumisoft.chatroom.dto.ChatMessageDTO;
import com.shumisoft.chatroom.redis.RedisMessagePublisher;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class ChatController {

    private final RedisMessagePublisher publisher;

    @MessageMapping("/sendMessage")
    public void sendMessage(ChatMessageDTO message) {
        publisher.publish(message);
    }

    @MessageMapping("/addUser")
    public void addUser(ChatMessageDTO message, SimpMessageHeaderAccessor accessor) {
        accessor.getSessionAttributes().put("username", message.getSender());
        message.setType(ChatMessageDTO.MessageType.JOIN);
        publisher.publish(message);
    }
}
