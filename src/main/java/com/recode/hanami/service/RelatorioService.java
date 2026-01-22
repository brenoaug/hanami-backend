package com.recode.hanami.service;

import com.recode.hanami.dto.*;
import com.recode.hanami.entities.Venda;
import com.recode.hanami.repository.VendaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RelatorioService {

    private static final Logger logger = LoggerFactory.getLogger(RelatorioService.class);

    private final VendaRepository vendaRepository;
    private final CalculadoraMetricasService calculadoraService;
    private final CalculosDemografiaRegiao calculosDemografiaRegiao;

    public RelatorioService(VendaRepository vendaRepository,
                            CalculadoraMetricasService calculadoraService,
                            CalculosDemografiaRegiao calculosDemografiaRegiao) {
        this.vendaRepository = vendaRepository;
        this.calculadoraService = calculadoraService;
        this.calculosDemografiaRegiao = calculosDemografiaRegiao;
    }

    public RelatorioCompletoDTO gerarRelatorioCompleto() {
        logger.info("Gerando relatório completo");

        List<Venda> todasVendas = vendaRepository.findAll();
        List<Venda> vendasComRelacoes = vendaRepository.findAllWithRelations();

        // Métricas Financeiras
        MetricasFinanceirasDTO metricasFinanceiras = gerarMetricasFinanceiras(todasVendas);

        // Análise de Produtos
        List<AnaliseProdutoDTO> analiseProdutos = gerarAnaliseProdutos(vendasComRelacoes);

        // Resumo de Vendas
        ResumoVendasDTO resumoVendas = gerarResumoVendas(todasVendas);

        // Desempenho Regional
        Map<String, MetricasRegiaoDTO> desempenhoRegional = calculosDemografiaRegiao.calcularMetricasPorRegiao(todasVendas);

        return new RelatorioCompletoDTO(
                LocalDateTime.now(),
                metricasFinanceiras,
                analiseProdutos,
                resumoVendas,
                desempenhoRegional
        );
    }

    private MetricasFinanceirasDTO gerarMetricasFinanceiras(List<Venda> vendas) {
        Double receitaLiquida = calculadoraService.calcularTotalVendas(vendas);
        Double custoTotal = calculadoraService.calcularCustoTotalGeral(vendas);
        Double lucroBruto = calculadoraService.calcularLucroBrutoGeral(vendas);

        return new MetricasFinanceirasDTO(receitaLiquida, custoTotal, lucroBruto);
    }

    private List<AnaliseProdutoDTO> gerarAnaliseProdutos(List<Venda> vendasComRelacoes) {
        Map<String, Map<String, Object>> produtosMap = new LinkedHashMap<>();

        for (Venda venda : vendasComRelacoes) {
            String nomeProduto = venda.getProduto().getNomeProduto();
            Integer quantidade = venda.getQuantidade() != null ? venda.getQuantidade() : 0;
            Double receita = venda.getValorFinal() != null ? venda.getValorFinal() : 0.0;

            if (produtosMap.containsKey(nomeProduto)) {
                Map<String, Object> produtoExistente = produtosMap.get(nomeProduto);
                Integer qtdAtual = (Integer) produtoExistente.get("quantidade_vendida");
                Double totalAtual = (Double) produtoExistente.get("total_arrecadado");

                produtoExistente.put("quantidade_vendida", qtdAtual + quantidade);
                produtoExistente.put("total_arrecadado", calculadoraService.arredondar(totalAtual + receita));
            } else {
                Map<String, Object> novoProduto = new LinkedHashMap<>();
                novoProduto.put("nome_produto", nomeProduto);
                novoProduto.put("quantidade_vendida", quantidade);
                novoProduto.put("total_arrecadado", calculadoraService.arredondar(receita));

                produtosMap.put(nomeProduto, novoProduto);
            }
        }

        // Converter para lista de DTOs e ordenar por total arrecadado (maior para menor)
        return produtosMap.values().stream()
                .map(map -> new AnaliseProdutoDTO(
                        (String) map.get("nome_produto"),
                        (Integer) map.get("quantidade_vendida"),
                        (Double) map.get("total_arrecadado")
                ))
                .sorted((a, b) -> b.totalArrecadado().compareTo(a.totalArrecadado()))
                .collect(Collectors.toList());
    }

    private ResumoVendasDTO gerarResumoVendas(List<Venda> vendas) {
        Integer numeroTotalVendas = calculadoraService.calcularNumeroTransacoes(vendas);
        Double valorMedioPorTransacao = calculadoraService.calcularMediaPorTransacao(vendas);
        String formaPagamentoMaisUtilizada = calculadoraService.calcularFormaPagamentoMaisUtilizada(vendas);
        String formaPagamentoMenosUtilizada = calculadoraService.calcularFormaPagamentoMenosUtilizada(vendas);
        String canalVendasMaisUtilizado = calculadoraService.calcularCanalVendasMaisUtilizado(vendas);
        String canalVendasMenosUtilizado = calculadoraService.calcularCanalVendasMenosUtilizado(vendas);

        return new ResumoVendasDTO(
                numeroTotalVendas,
                valorMedioPorTransacao,
                formaPagamentoMaisUtilizada,
                formaPagamentoMenosUtilizada,
                canalVendasMaisUtilizado,
                canalVendasMenosUtilizado
        );
    }
}
