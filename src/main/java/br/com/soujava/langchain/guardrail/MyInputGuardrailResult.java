package br.com.soujava.langchain.guardrail;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.guardrail.InputGuardrail;
import dev.langchain4j.guardrail.InputGuardrailResult;
import org.springframework.web.context.annotation.ApplicationScope;

@ApplicationScope
public class MyInputGuardrailResult implements InputGuardrail {
    @Override
    public InputGuardrailResult validate(UserMessage userMessage) {
        if(userMessage.singleText().contains("palavra chave")){
            return fatal("Detectada palavra chave");
        }
        return success();
    }
}
