package br.com.soujava.langchain.controller;

import br.com.soujava.langchain.ai.Assistant;
import br.com.soujava.langchain.dto.ChatBotRequest;
import br.com.soujava.langchain.dto.ChatBotResponse;
import dev.langchain4j.guardrail.InputGuardrailException;
import dev.langchain4j.guardrail.OutputGuardrailException;
import dev.langchain4j.service.Result;
import dev.langchain4j.service.spring.AiService;
import org.springframework.web.bind.annotation.*;

/**
 * This is an example of using an {@link AiService}, a high-level LangChain4j API.
 */
@RestController
public class AssistantController {

    private final Assistant assistant;

    public AssistantController(Assistant assistant) {
        this.assistant = assistant;
    }

    @PostMapping(value = "/chatbot/message", produces = "application/json", consumes = "application/json")
    public ChatBotResponse assistant(@RequestBody ChatBotRequest request) {
        try {
            Result<String> answer = assistant.answer(request.getMemoryId(), request.getMessage());
            return new ChatBotResponse(request.getMemoryId(), answer.content());
        } catch (InputGuardrailException | OutputGuardrailException e) {
            return new ChatBotResponse(request.getMemoryId(), e.getMessage());
        }
    }
}