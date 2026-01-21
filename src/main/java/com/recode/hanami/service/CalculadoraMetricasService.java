package com.recode.hanami.service;

import com.recode.hanami.entities.Produto;
import com.recode.hanami.entities.Venda;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CalculadoraMetricasService {

    public Double calcularCustoTotalVenda(Venda venda) {
        Produto produto = venda.getProduto();

        if (produto == null || venda.getQuantidade() == null) {
            return 0.0;
        }

        double precoUnitario = produto.getPrecoUnitario() != null ? produto.getPrecoUnitario() : 0.0;
        double margem = produto.getMargemLucro() != null ? produto.getMargemLucro() : 0.0;
        int quantidade = venda.getQuantidade();

        if (margem == -1.0) return 0.0;

        double custoUnitarioEstimado = precoUnitario / (1 + margem);

        return custoUnitarioEstimado * quantidade;
    }

    public Double calcularTotalVendas(List<Venda> vendas) {
        if (vendas == null || vendas.isEmpty()) {
            return 0.0;
        }
        double soma = vendas.stream()
                .mapToDouble(v -> v.getValorFinal() != null ? v.getValorFinal() : 0.0)
                .sum();

        return arredondar(soma);
    }

    public Integer calcularNumeroTransacoes(List<Venda> vendas) {
        if (vendas == null) {
            return 0;
        }
        return vendas.size();
    }

    public Double calcularMediaPorTransacao(List<Venda> vendas) {
        if (vendas == null || vendas.isEmpty()) {
            return 0.0;
        }

        Double total = calcularTotalVendas(vendas);
        Integer quantidade = calcularNumeroTransacoes(vendas);

        if (quantidade == 0) return 0.0;

        return arredondar(total / quantidade);
    }

    public Double arredondar(Double valor) {
        if (valor == null) return 0.0;
        BigDecimal bd = BigDecimal.valueOf(valor);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public Double calcularCustoTotalGeral(List<Venda> vendas) {
        if (vendas == null || vendas.isEmpty()) return 0.0;

        double somaCustos = vendas.stream()
                .mapToDouble(this::calcularCustoTotalVenda) // Reusa a lógica unitária
                .sum();

        return arredondar(somaCustos);
    }

    public Double calcularLucroBrutoGeral(List<Venda> vendas) {
        if (vendas == null || vendas.isEmpty()) return 0.0;

        double receitaTotal = vendas.stream()
                .mapToDouble(v -> v.getValorFinal() != null ? v.getValorFinal() : 0.0)
                .sum();

        double custoTotal = vendas.stream()
                .mapToDouble(this::calcularCustoTotalVenda)
                .sum();

        return arredondar(receitaTotal - custoTotal);
    }

    public String calcularFormaPagamentoMaisUtilizada(List<Venda> vendas) {
        if (vendas == null || vendas.isEmpty()) {
            return "N/A";
        }

        Map<String, Long> contagemFormasPagamento = vendas.stream()
                .collect(Collectors.groupingBy(
                        v -> v.getFormaPagamento() != null ? v.getFormaPagamento() : "Não informado",
                        Collectors.counting()
                ));

        return contagemFormasPagamento.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }

    public String calcularFormaPagamentoMenosUtilizada(List<Venda> vendas) {
        if (vendas == null || vendas.isEmpty()) {
            return "N/A";
        }

        Map<String, Long> contagemFormasPagamento = vendas.stream()
                .collect(Collectors.groupingBy(
                        v -> v.getFormaPagamento() != null ? v.getFormaPagamento() : "Não informado",
                        Collectors.counting()
                ));

        return contagemFormasPagamento.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }

    public String calcularCanalVendasMaisUtilizado(List<Venda> vendas) {
        if (vendas == null || vendas.isEmpty()) {
            return "N/A";
        }

        Map<String, Long> contagemCanais = vendas.stream()
                .collect(Collectors.groupingBy(
                        v -> v.getCanalVenda() != null ? v.getCanalVenda() : "Não informado",
                        Collectors.counting()
                ));

        return contagemCanais.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }

    public String calcularCanalVendasMenosUtilizado(List<Venda> vendas) {
        if (vendas == null || vendas.isEmpty()) {
            return "N/A";
        }

        Map<String, Long> contagemCanais = vendas.stream()
                .collect(Collectors.groupingBy(
                        v -> v.getCanalVenda() != null ? v.getCanalVenda() : "Não informado",
                        Collectors.counting()
                ));

        return contagemCanais.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }
}