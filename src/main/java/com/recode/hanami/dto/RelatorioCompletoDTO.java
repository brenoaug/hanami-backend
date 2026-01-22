package com.recode.hanami.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record RelatorioCompletoDTO(
        @JsonProperty("data_geracao")
        LocalDateTime dataGeracao,

        @JsonProperty("metricas_financeiras")
        MetricasFinanceirasDTO metricasFinanceiras,

        @JsonProperty("analise_produtos")
        List<AnaliseProdutoDTO> analiseProdutos,

        @JsonProperty("resumo_vendas")
        ResumoVendasDTO resumoVendas,

        @JsonProperty("desempenho_regional")
        Map<String, MetricasRegiaoDTO> desempenhoRegional
) {
}
