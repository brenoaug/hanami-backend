package com.recode.hanami.exception.handler;

import com.lowagie.text.DocumentException;
import com.recode.hanami.dto.ErrorResponseDTO;
import com.recode.hanami.exception.ArquivoInvalidoException;
import com.recode.hanami.exception.DadosInvalidosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.IOException;

/**
 * Handler global de exceções para toda a aplicação.
 * Centraliza o tratamento de erros e padroniza as respostas usando ErrorResponseDTO.
 *
 * Segue os princípios:
 * - DRY (Don't Repeat Yourself) - Método auxiliar buildErrorResponse()
 * - Single Responsibility - Apenas trata exceções
 * - Type Safety - Usa DTO tipado ao invés de Map
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Trata exceções de arquivo inválido (nulo, vazio, extensão errada).
     * Retorna HTTP 400 Bad Request.
     */
    @ExceptionHandler(ArquivoInvalidoException.class)
    public ResponseEntity<ErrorResponseDTO> handleArquivoInvalido(ArquivoInvalidoException ex) {
        logger.warn("Arquivo inválido: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ErrorType.ERRO, ex.getMessage());
    }

    /**
     * Trata exceções de dados inválidos no conteúdo do arquivo.
     * Retorna HTTP 422 Unprocessable Entity.
     */
    @ExceptionHandler(DadosInvalidosException.class)
    public ResponseEntity<ErrorResponseDTO> handleDadosInvalidos(DadosInvalidosException ex) {
        logger.error("Dados inválidos: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, ErrorType.ERRO_PROCESSAMENTO, ex.getMessage());
    }

    /**
     * Trata exceções de arquivo maior que o limite permitido.
     * Retorna HTTP 413 Payload Too Large.
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponseDTO> handleMaxUploadSizeExceeded(MaxUploadSizeExceededException ex) {
        logger.error("Arquivo excede o tamanho máximo permitido: {}", ex.getMessage());
        return buildErrorResponse(
            HttpStatus.PAYLOAD_TOO_LARGE,
            ErrorType.ERRO,
            "O arquivo enviado excede o tamanho máximo permitido."
        );
    }

    /**
     * Trata exceções de geração de PDF/JSON.
     * Retorna HTTP 500 Internal Server Error.
     */
    @ExceptionHandler({DocumentException.class, IOException.class})
    public ResponseEntity<ErrorResponseDTO> handleDocumentOrIOException(Exception ex) {
        logger.error("Erro ao gerar documento: {}", ex.getMessage(), ex);
        return buildErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            ErrorType.ERRO_INTERNO,
            "Erro ao gerar relatório: " + ex.getMessage()
        );
    }

    /**
     * Trata exceções genéricas não capturadas pelos handlers específicos.
     * Retorna HTTP 500 Internal Server Error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        logger.error("Erro inesperado durante o processamento: {}", ex.getMessage(), ex);
        return buildErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            ErrorType.ERRO_INTERNO,
            "Ocorreu um erro inesperado: " + ex.getMessage()
        );
    }

    /**
     * Método auxiliar que constrói a resposta de erro padronizada.
     * Aplica o princípio DRY (Don't Repeat Yourself).
     *
     * @param status Código HTTP de status
     * @param errorType Tipo/categoria do erro
     * @param mensagem Mensagem descritiva do erro
     * @return ResponseEntity com ErrorResponseDTO
     */
    private ResponseEntity<ErrorResponseDTO> buildErrorResponse(
            HttpStatus status,
            ErrorType errorType,
            String mensagem) {

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(errorType.getValue(), mensagem);
        return ResponseEntity.status(status).body(errorResponse);
    }
}
