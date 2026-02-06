package br.com.soujava.langchain.tools;

import br.com.soujava.langchain.service.VectorSearchService;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component
public class ProductTools {
    private final VectorSearchService vectorSearchService;

    public ProductTools(VectorSearchService vectorSearchService) {
        this.vectorSearchService = vectorSearchService;
    }

    @Tool("Busca informações semânticas na base de conhecimento")
    public String searchProducts(String query) {
        return vectorSearchService.search(query);
    }
}