package br.com.soujava.langchain.tools;

import dev.langchain4j.agent.tool.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class IssueManagementTools {
    private static final Logger log = LoggerFactory.getLogger(IssueManagementTools.class);

    @Tool("Registra um chamado de suporte")
    public String openIssue(String description) {
        log.info(description);
        return "Chamado registrado com sucesso. Protocolo #12345";
    }
}