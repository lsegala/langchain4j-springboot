package br.com.soujava.langchain.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.qdrant.QdrantEmbeddingStore;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfiguration {
    public static final int MAX_MESSAGES = 10;
    public static final int MAX_RESULTS = 3;
    public static final double MIN_SCORE = 0.7;

    @Value("${database.host}")
    private String host;
    @Value("${database.port}")
    private Integer port;
    @Value("${database.collection-name}")
    private String collectionName;
    @Value("${open-ai.model-name}")
    private String embeddingModelName;

    @Bean
    EmbeddingStore<TextSegment> embeddingStore() {
        return QdrantEmbeddingStore.builder()
                .host(host)
                .port(port)
                .collectionName(collectionName)
                .build();
    }

    @Bean
    EmbeddingModel embeddingModel() {
        return OpenAiEmbeddingModel.builder()
                .apiKey(System.getenv("OPENAI_API_KEY"))
                .modelName(embeddingModelName)
                .build();
    }

    @Bean
    EmbeddingStoreIngestor embeddingStoreIngestor(
            EmbeddingStore<TextSegment> embeddingStore,
            EmbeddingModel embeddingModel) {

        return EmbeddingStoreIngestor.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .build();
    }

    @Bean
    ChatMemoryStore chatMemoryStore(){
        return new InMemoryChatMemoryStore();
    }

    @Bean
    ChatMemoryProvider chatMemoryProvider(ChatMemoryStore jpaChatMemoryStore) {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .chatMemoryStore(jpaChatMemoryStore)
                .maxMessages(MAX_MESSAGES)
                .build();
    }

    @Bean
    ContentRetriever contentRetriever(
            EmbeddingStore<TextSegment> embeddingStore,
            EmbeddingModel embeddingModel) {

        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(MAX_RESULTS)          // quantos “trechos” recuperar
                .minScore(MIN_SCORE)          // opcional: threshold semântico
                .build();
    }
}
