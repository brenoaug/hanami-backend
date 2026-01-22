package com.recode.hanami.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * DTO para respostas de erro padronizadas da API.
 * Utilizado pelo GlobalExceptionHandler para retornar erros consistentes.
 */
@Schema(description = "Resposta de erro padrão da API")
public record ErrorResponseDTO(

        @Schema(description = "Tipo/categoria do erro", example = "erro_validacao")
        String status,

        @Schema(description = "Mensagem descritiva do erro", example = "O arquivo deve ter a extensão .csv")
        String mensagem,

        @Schema(description = "Timestamp de quando o erro ocorreu", example = "2026-01-22T10:30:00")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime timestamp
) {
    /**
     * Construtor de conveniência que define o timestamp automaticamente.
     */
    public ErrorResponseDTO(String status, String mensagem) {
        this(status, mensagem, LocalDateTime.now());
    }
}
