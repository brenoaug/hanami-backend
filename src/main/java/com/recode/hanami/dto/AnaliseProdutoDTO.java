package com.recode.hanami.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AnaliseProdutoDTO(
        @JsonProperty("nome_produto")
        String nomeProduto,

        @JsonProperty("quantidade_vendida")
        Integer quantidadeVendida,

        @JsonProperty("total_arrecadado")
        Double totalArrecadado
) {
}
