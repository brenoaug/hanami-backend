package com.recode.hanami.service;

import com.recode.hanami.dto.DistribuicaoClientesDTO;
import com.recode.hanami.dto.ItemDistribuicaoDTO;
import com.recode.hanami.dto.MetricasRegiaoDTO;
import com.recode.hanami.entities.Venda;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CalculosDemografiaRegiao {

    public Map<String, MetricasRegiaoDTO> calcularMetricasPorRegiao(List<Venda> vendas) {
        if (vendas == null || vendas.isEmpty()) {
            return new LinkedHashMap<>();
        }

        return vendas.stream()
                .filter(venda -> venda.getRegiao() != null && !venda.getRegiao().trim().isEmpty())
                .collect(Collectors.groupingBy(
                        Venda::getRegiao,
                        LinkedHashMap::new,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                this::calcularMetricas
                        )
                ));
    }

    public Map<String, MetricasRegiaoDTO> calcularMetricasPorEstado(List<Venda> vendas) {
        if (vendas == null || vendas.isEmpty()) {
            return new LinkedHashMap<>();
        }

        return vendas.stream()
                .filter(venda -> venda.getCliente() != null
                        && venda.getCliente().getEstadoCliente() != null
                        && !venda.getCliente().getEstadoCliente().trim().isEmpty())
                .collect(Collectors.groupingBy(
                        venda -> venda.getCliente().getEstadoCliente().toUpperCase(),
                        LinkedHashMap::new,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                this::calcularMetricas
                        )
                ));
    }

    private MetricasRegiaoDTO calcularMetricas(List<Venda> vendasRegiao) {
        long totalTransacoes = vendasRegiao.size();

        double receitaTotal = vendasRegiao.stream()
                .mapToDouble(v -> v.getValorFinal() != null ? v.getValorFinal() : 0.0)
                .sum();

        int quantidadeVendida = vendasRegiao.stream()
                .mapToInt(v -> v.getQuantidade() != null ? v.getQuantidade() : 0)
                .sum();

        double mediaValorTransacao = totalTransacoes > 0 ? receitaTotal / totalTransacoes : 0.0;

        return new MetricasRegiaoDTO(
                totalTransacoes,
                arredondar(receitaTotal),
                quantidadeVendida,
                arredondar(mediaValorTransacao)
        );
    }


    public DistribuicaoClientesDTO calcularDistribuicaoClientes(List<Venda> vendas) {
        if (vendas == null || vendas.isEmpty()) {
            return new DistribuicaoClientesDTO(
                    new LinkedHashMap<>(),
                    new LinkedHashMap<>(),
                    new LinkedHashMap<>()
            );
        }

        long totalClientes = vendas.stream()
                .map(v -> v.getCliente() != null ? v.getCliente().getId() : null)
                .filter(Objects::nonNull)
                .distinct()
                .count();

        // Distribuição por gênero (contando clientes únicos)
        Map<String, Long> contagemGenero = vendas.stream()
                .filter(v -> v.getCliente() != null && v.getCliente().getGeneroCliente() != null)
                .collect(Collectors.groupingBy(
                        v -> v.getCliente().getGeneroCliente(),
                        Collectors.mapping(
                                v -> v.getCliente().getId(),
                                Collectors.collectingAndThen(Collectors.toSet(), Set::size)
                        )
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> Long.valueOf(e.getValue()),
                        (a, b) -> a,
                        LinkedHashMap::new
                ));

        Map<String, ItemDistribuicaoDTO> distribuicaoGenero = contagemGenero.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new ItemDistribuicaoDTO(
                                entry.getValue(),
                                calcularPercentual(entry.getValue(), totalClientes)
                        ),
                        (a, b) -> a,
                        LinkedHashMap::new
                ));

        // Distribuição por faixa etária (contando clientes únicos)
        Map<String, Long> contagemFaixaEtaria = vendas.stream()
                .filter(v -> v.getCliente() != null && v.getCliente().getIdadeCliente() != null)
                .collect(Collectors.groupingBy(
                        v -> classificarFaixaEtaria(v.getCliente().getIdadeCliente()),
                        Collectors.mapping(
                                v -> v.getCliente().getId(),
                                Collectors.collectingAndThen(Collectors.toSet(), Set::size)
                        )
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> Long.valueOf(e.getValue()),
                        (a, b) -> a,
                        LinkedHashMap::new
                ));

        Map<String, ItemDistribuicaoDTO> distribuicaoFaixaEtaria = contagemFaixaEtaria.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new ItemDistribuicaoDTO(
                                entry.getValue(),
                                calcularPercentual(entry.getValue(), totalClientes)
                        ),
                        (a, b) -> a,
                        LinkedHashMap::new
                ));

        // Distribuição por cidade (contando clientes únicos)
        Map<String, Long> contagemCidade = vendas.stream()
                .filter(v -> v.getCliente() != null && v.getCliente().getCidadeCliente() != null)
                .collect(Collectors.groupingBy(
                        v -> v.getCliente().getCidadeCliente(),
                        Collectors.mapping(
                                v -> v.getCliente().getId(),
                                Collectors.collectingAndThen(Collectors.toSet(), Set::size)
                        )
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> Long.valueOf(e.getValue()),
                        (a, b) -> a,
                        LinkedHashMap::new
                ));

        Map<String, ItemDistribuicaoDTO> distribuicaoCidade = contagemCidade.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new ItemDistribuicaoDTO(
                                entry.getValue(),
                                calcularPercentual(entry.getValue(), totalClientes)
                        ),
                        (a, b) -> a,
                        LinkedHashMap::new
                ));

        return new DistribuicaoClientesDTO(distribuicaoGenero, distribuicaoFaixaEtaria, distribuicaoCidade);
    }


    private String classificarFaixaEtaria(Integer idade) {
        if (idade == null) return "Não informado";
        if (idade < 18) return "Menor de 18";
        if (idade <= 25) return "18-25";
        if (idade <= 35) return "26-35";
        if (idade <= 45) return "36-45";
        if (idade <= 55) return "46-55";
        if (idade <= 65) return "56-65";
        return "Acima de 65";
    }

    private double calcularPercentual(long valor, long total) {
        if (total == 0) return 0.0;
        double percentual = (valor * 100.0) / total;
        return arredondar(percentual);
    }

    private double arredondar(double valor) {
        BigDecimal bd = BigDecimal.valueOf(valor);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
