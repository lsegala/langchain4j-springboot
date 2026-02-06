package br.com.soujava.langchain.service;

import com.opencsv.CSVReader;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class VectorIngestService {
    private final EmbeddingStore<TextSegment> embeddingStore;
    private final EmbeddingModel embeddingModel;

    public VectorIngestService(
            EmbeddingStore<TextSegment> embeddingStore,
            EmbeddingModel embeddingModel) {

        this.embeddingStore = embeddingStore;
        this.embeddingModel = embeddingModel;
    }

    public void save(MultipartFile file) throws Exception {
        List<Document> documents = parseCsvToDocuments(file);

        EmbeddingStoreIngestor ingestor =
                EmbeddingStoreIngestor.builder()
                        .embeddingStore(embeddingStore)
                        .embeddingModel(embeddingModel)
                        .build();
        ingestor.ingest(documents);
    }

    private List<Document> parseCsvToDocuments(MultipartFile file) throws Exception {

        List<Document> documents = new ArrayList<>();

        try (CSVReader reader = new CSVReader(
                new InputStreamReader(file.getInputStream()))) {

            String[] header = reader.readNext(); // header
            String[] line;

            while ((line = reader.readNext()) != null) {

                String content = buildSemanticText(header, line);

                Document document = Document.from(
                        content,
                        Metadata.metadata("source", "csv")
                );

                documents.add(document);
            }
        }

        return documents;
    }

    private String buildSemanticText(String[] header, String[] values) {

        StringBuilder sb = new StringBuilder("Registro: ");

        for (int i = 0; i < header.length; i++) {
            sb.append(header[i])
                    .append(" = ")
                    .append(values[i])
                    .append("; ");
        }

        return sb.toString();
    }

}
