package com.recode.hanami.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record MetricasRegiaoDTO(long totalTransacoes, double receitaTotal, int quantidadeVendida,
                                double mediaValorTransacao) {

    @Override
    @JsonProperty("totalTransacoes")
    public long totalTransacoes() {
        return totalTransacoes;
    }

    @Override
    @JsonProperty("receitaTotal")
    public double receitaTotal() {
        return receitaTotal;
    }

    @Override
    @JsonProperty("quantidadeVendida")
    public int quantidadeVendida() {
        return quantidadeVendida;
    }

    @Override
    @JsonProperty("mediaValorTransacao")
    public double mediaValorTransacao() {
        return mediaValorTransacao;
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "MetricasRegiao{" +
                "totalTransacoes=" + totalTransacoes +
                ", receitaTotal=" + receitaTotal +
                ", quantidadeVendida=" + quantidadeVendida +
                ", mediaValorTransacao=" + mediaValorTransacao +
                '}';
    }
}