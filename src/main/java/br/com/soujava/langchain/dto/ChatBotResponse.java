package br.com.soujava.langchain.dto;

public class ChatBotResponse {
    private String memoryId;
    private String responseMessage;

    public ChatBotResponse(String memoryId, String responseMessage) {
        this.memoryId = memoryId;
        this.responseMessage = responseMessage;
    }

    public ChatBotResponse() {
    }

    public String getMemoryId() {
        return memoryId;
    }

    public void setMemoryId(String memoryId) {
        this.memoryId = memoryId;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
