package com.recode.hanami.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MetricasFinanceirasDTO(
        @JsonProperty("receita_liquida")
        Double receitaLiquida,

        @JsonProperty("custo_total")
        Double custoTotal,

        @JsonProperty("lucro_bruto")
        Double lucroBruto
) {
}
