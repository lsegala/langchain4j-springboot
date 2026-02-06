package br.com.soujava.langchain.controller;

import br.com.soujava.langchain.service.VectorIngestService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/documents")
public class IngestionController {
    private final VectorIngestService vectorIngestService;

    public IngestionController(VectorIngestService vectorIngestService) {
        this.vectorIngestService = vectorIngestService;
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadCsv(
            @RequestPart("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Arquivo CSV não pode estar vazio");
        }

        if (!file.getOriginalFilename().endsWith(".csv")) {
            return ResponseEntity.badRequest()
                    .body("Somente arquivos CSV são permitidos");
        }

        try {
            vectorIngestService.save(file);
            return ResponseEntity.ok("Arquivo processado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erro ao processar CSV: " + e.getMessage());
        }
    }
}
