package com.recode.hanami.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ItemDistribuicaoDTO(long contagem, double percentual) {

    @Override
    @JsonProperty("contagem")
    public long contagem() {
        return contagem;
    }

    @Override
    @JsonProperty("percentual")
    public double percentual() {
        return percentual;
    }
}