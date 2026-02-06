package br.com.soujava.langchain.guardrail;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.guardrail.OutputGuardrail;
import dev.langchain4j.guardrail.OutputGuardrailResult;
import org.springframework.web.context.annotation.ApplicationScope;

@ApplicationScope
public class MyOutputGuardrailResult implements OutputGuardrail {
    @Override
    public OutputGuardrailResult validate(AiMessage responseFromLLM) {
        String message = responseFromLLM.text();
        if(message.contains("alguma palavra especial")){
            return fatal("Ocorreu um problema ao gerar a resposta");
        }
        return success();
    }
}
