package br.com.soujava.langchain.dto;

public class ChatBotRequest {
    private String memoryId;
    private String message;

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
}
