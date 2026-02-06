package br.com.soujava.langchain.dto;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageType;

import java.util.LinkedList;
import java.util.List;

import static dev.langchain4j.data.message.ChatMessageDeserializer.messagesFromJson;

public class ChatBotMessage {
    private String memoryId;
    private String message;
    private boolean isUser;

    public String getMemoryId() {
        return memoryId;
    }

    public void setMemoryId(String memoryId) {
        this.memoryId = memoryId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean user) {
        isUser = user;
    }

    public ChatBotMessage(String memoryId, String message, boolean isUser) {
        this.memoryId = memoryId;
        this.message = message;
        this.isUser = isUser;
    }

    public static List<ChatBotMessage> fromJson(String memoryId, String messages) {
        final List<ChatBotMessage> chatBotMessages = new LinkedList<>();
        List<ChatMessage> chatMessages = messagesFromJson(messages);
        chatMessages.forEach(chatMessage -> chatBotMessages
                .add(new ChatBotMessage(memoryId, chatMessage.toString(), chatMessage.type().equals(ChatMessageType.USER))));
        return chatBotMessages;
    }
}