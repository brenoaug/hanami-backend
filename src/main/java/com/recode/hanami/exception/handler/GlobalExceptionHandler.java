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

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ArquivoInvalidoException.class)
    public ResponseEntity<ErrorResponseDTO> handleArquivoInvalido(ArquivoInvalidoException ex) {
        logger.warn("Arquivo inválido: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ErrorType.ERRO, ex.getMessage());
    }

    @ExceptionHandler(DadosInvalidosException.class)
    public ResponseEntity<ErrorResponseDTO> handleDadosInvalidos(DadosInvalidosException ex) {
        logger.error("Dados inválidos: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, ErrorType.ERRO_PROCESSAMENTO, ex.getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponseDTO> handleMaxUploadSizeExceeded(MaxUploadSizeExceededException ex) {
        logger.error("Arquivo excede o tamanho máximo permitido: {}", ex.getMessage());
        return buildErrorResponse(
            HttpStatus.PAYLOAD_TOO_LARGE,
            ErrorType.ERRO,
            "O arquivo enviado excede o tamanho máximo permitido."
        );
    }

    @ExceptionHandler({DocumentException.class, IOException.class})
    public ResponseEntity<ErrorResponseDTO> handleDocumentOrIOException(Exception ex) {
        logger.error("Erro ao gerar documento: {}", ex.getMessage(), ex);
        return buildErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            ErrorType.ERRO_INTERNO,
            "Erro ao gerar relatório: " + ex.getMessage()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        logger.error("Erro inesperado durante o processamento: {}", ex.getMessage(), ex);
        return buildErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            ErrorType.ERRO_INTERNO,
            "Ocorreu um erro inesperado: " + ex.getMessage()
        );
    }

    private ResponseEntity<ErrorResponseDTO> buildErrorResponse(
            HttpStatus status,
            ErrorType errorType,
            String mensagem) {

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(errorType.getValue(), mensagem);
        return ResponseEntity.status(status).body(errorResponse);
    }
}
