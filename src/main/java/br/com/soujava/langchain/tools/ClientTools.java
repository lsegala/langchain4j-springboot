package br.com.soujava.langchain.tools;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component
public class ClientTools {
    @Tool("Consulta dados de clientes pelo Nome")
    public String findClientByName(String name) {
        return "Cliente: João Silva, Plano: Premium, Status: Ativo";
    }

    @Tool("Valida se um cliente pode acessar um benefício")
    public String validateBenefit(String plan) {
        if ("Premium".equalsIgnoreCase(plan)) {
            return "Cliente elegível para o benefício.";
        }
        return "Cliente não elegível.";
    }
}