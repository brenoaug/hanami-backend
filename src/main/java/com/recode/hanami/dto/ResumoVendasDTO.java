package com.recode.hanami.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResumoVendasDTO(
        @JsonProperty("numero_total_vendas")
        Integer numeroTotalVendas,

        @JsonProperty("valor_medio_por_transacao")
        Double valorMedioPorTransacao,

        @JsonProperty("forma_pagamento_mais_utilizada")
        String formaPagamentoMaisUtilizada,

        @JsonProperty("forma_pagamento_menos_utilizada")
        String formaPagamentoMenosUtilizada,

        @JsonProperty("canal_vendas_mais_utilizado")
        String canalVendasMaisUtilizado,

        @JsonProperty("canal_vendas_menos_utilizado")
        String canalVendasMenosUtilizado
) {
}
