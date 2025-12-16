package com.recode.hanami.controller;

import com.recode.hanami.dto.DadosArquivoDTO;
import com.recode.hanami.dto.ImportacaoResponseDTO;
import com.recode.hanami.exceptions.ArquivoInvalidoException;
import com.recode.hanami.service.CsvService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/csv")
public class CsvController {

    private final CsvService csvService;

    public CsvController(CsvService csvService) {
        this.csvService = csvService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadCsv(@RequestParam(value = "file", required = false) MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "status", "erro",
                            "mensagem", "Nenhum arquivo foi enviado ou o arquivo está vazio."
                    ));
        }

        try {
            List<DadosArquivoDTO> listaProcessada = csvService.conversorCsvParaJson(file);
            ImportacaoResponseDTO resposta = new ImportacaoResponseDTO("sucesso", listaProcessada.size());
            return ResponseEntity.ok(resposta);

        } catch (ArquivoInvalidoException e) {
            return ResponseEntity
                    .status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(Map.of(
                            "status", "erro_processamento",
                            "mensagem", e.getMessage()
                    ));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                    "status", "erro_interno",
                    "mensagem", "Ocorreu um erro inesperado: " + e.getMessage()
            ));
        }
    }
}