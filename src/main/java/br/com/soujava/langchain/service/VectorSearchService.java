package br.com.soujava.langchain.service;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class VectorSearchService {
    public static final int MAX_RESULTS = 5;
    public static final double MIN_SCORE = 0.6;
    private final EmbeddingStore<TextSegment> embeddingStore;
    private final EmbeddingModel embeddingModel;

    public VectorSearchService(
            EmbeddingStore<TextSegment> embeddingStore,
            EmbeddingModel embeddingModel) {
        this.embeddingStore = embeddingStore;
        this.embeddingModel = embeddingModel;
    }

    public String search(String query) {

        // 1️⃣ Gerar embedding da query (CORRETAMENTE)
        Response<Embedding> response = embeddingModel.embed(query);

        if (response == null) {
            return "Erro ao gerar embedding da consulta.";
        }

        Embedding queryEmbedding = response.content();

        // 2️⃣ Buscar no banco vetorial
        EmbeddingSearchResult<TextSegment> matches =
                embeddingStore.search(
                        EmbeddingSearchRequest.builder()
                                .queryEmbedding(queryEmbedding)
                                .maxResults(MAX_RESULTS)
                                .minScore(MIN_SCORE)
                                .build()
                );

        if (matches.matches().isEmpty()) {
            return "Nenhum resultado encontrado.";
        }

        // 3️⃣ Retornar conteúdo para o LLM
        return matches.matches().stream()
                .map(match -> match.embedded().text())
                .collect(Collectors.joining("\n"));
    }
}