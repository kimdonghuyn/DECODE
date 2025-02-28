package com.example.mailauth_practice.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class ChatController {
    @MessageMapping("/chat")
    @SendTo("/sub/chat")
    public Map<String, Object> sendChatMessage(Map<String, Object> message) {
        System.out.println("📩 받은 메시지: " + message);

        return message;
    }
}
