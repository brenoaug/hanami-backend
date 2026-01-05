package com.recode.hanami.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ImportacaoResponseDTO(
        String status,

        @JsonProperty("linhas_processadas")
        Integer linhasProcessadas
) {
}