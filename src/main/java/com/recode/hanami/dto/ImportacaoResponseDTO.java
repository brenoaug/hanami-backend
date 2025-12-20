package com.recode.hanami.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImportacaoResponseDTO {

    private final String status;

    @JsonProperty("linhas_processadas")
    private Integer linhasProcessadas;

    public ImportacaoResponseDTO(String status, Integer linhasProcessadas) {
        this.status = status;
        this.linhasProcessadas = linhasProcessadas;
    }

    // Getters
    public String getStatus() { return status; }
    public Integer getLinhasProcessadas() { return linhasProcessadas; }
}