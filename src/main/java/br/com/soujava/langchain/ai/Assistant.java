package br.com.soujava.langchain.ai;

import br.com.soujava.langchain.guardrail.MyInputGuardrailResult;
import br.com.soujava.langchain.guardrail.MyOutputGuardrailResult;
import dev.langchain4j.service.*;
import dev.langchain4j.service.guardrail.InputGuardrails;
import dev.langchain4j.service.guardrail.OutputGuardrails;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface Assistant {
    @SystemMessage("""
    Você é um assistente que só pode responder usando informações da base de conhecimento.
    Clientes do plano Premium possuem 10% de desconto nos preços dos produtos.
    """)
    @InputGuardrails({MyInputGuardrailResult.class})
    @OutputGuardrails(MyOutputGuardrailResult.class)
    @UserMessage("<userMessage>{{userMessage}}</userMessage>")
    Result<String> answer(@MemoryId String memoryId, @V("userMessage")String userMessage);
}