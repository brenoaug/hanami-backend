package com.recode.hanami.controller;

import com.recode.hanami.controller.docs.CsvControllerOpenApi;
import com.recode.hanami.dto.DadosArquivoDTO;
import com.recode.hanami.dto.ImportacaoResponseDTO;
import com.recode.hanami.service.CsvService;
import com.recode.hanami.service.ProcessamentoVendasService;
import com.recode.hanami.validation.UploadArquivoValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Controller responsável pelo upload e processamento de arquivos CSV.
 * Implementa apenas o fluxo feliz (Happy Path).
 * Exceções são tratadas pelo GlobalExceptionHandler.
 */
@RestController
@RequestMapping("/hanami")
public class CsvController implements CsvControllerOpenApi {

    private static final Logger logger = LoggerFactory.getLogger(CsvController.class);

    private final CsvService csvService;
    private final ProcessamentoVendasService processamentoVendasService;
    private final UploadArquivoValidator uploadArquivoValidator;

    public CsvController(CsvService csvService,
                         ProcessamentoVendasService processamentoVendasService,
                         UploadArquivoValidator uploadArquivoValidator) {
        this.csvService = csvService;
        this.processamentoVendasService = processamentoVendasService;
        this.uploadArquivoValidator = uploadArquivoValidator;
    }

    @PostMapping(value = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public ResponseEntity<ImportacaoResponseDTO> uploadCsv(@RequestParam(value = "file") MultipartFile file) {
        logger.info("Iniciando processamento de upload de arquivo: {}", file.getOriginalFilename());

        // Validar arquivo (lança exceção se inválido)
        uploadArquivoValidator.validate(file);

        // Converter CSV para JSON
        List<DadosArquivoDTO> listaProcessada = csvService.conversorCsvParaJson(file);
        logger.debug("Arquivo convertido com sucesso. Registros: {}", listaProcessada.size());

        // Salvar dados no banco
        processamentoVendasService.salvarDadosDoArquivo(listaProcessada);
        logger.info("Dados salvos com sucesso. Total de registros: {}", listaProcessada.size());

        // Retornar resposta de sucesso
        ImportacaoResponseDTO resposta = new ImportacaoResponseDTO("sucesso", listaProcessada.size());
        return ResponseEntity.ok(resposta);
    }
}

