package com.recode.hanami.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record DistribuicaoClientesDTO(Map<String, ItemDistribuicaoDTO> porGenero,
                                      Map<String, ItemDistribuicaoDTO> porFaixaEtaria,
                                      Map<String, ItemDistribuicaoDTO> porCidade) {

    @Override
    @JsonProperty("por_genero")
    public Map<String, ItemDistribuicaoDTO> porGenero() {
        return porGenero;
    }

    @Override
    @JsonProperty("por_faixa_etaria")
    public Map<String, ItemDistribuicaoDTO> porFaixaEtaria() {
        return porFaixaEtaria;
    }

    @Override
    @JsonProperty("por_cidade")
    public Map<String, ItemDistribuicaoDTO> porCidade() {
        return porCidade;
    }
}
