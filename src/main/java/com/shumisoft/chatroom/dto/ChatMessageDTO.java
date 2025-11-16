package com.shumisoft.chatroom.dto;

import lombok.Data;

@Data
public class ChatMessageDTO {
    private String sender;
    private String content;
    private MessageType type;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
}
