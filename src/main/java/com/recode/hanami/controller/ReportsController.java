package com.recode.hanami.controller;

import com.recode.hanami.dto.DistribuicaoClientesDTO;
import com.recode.hanami.dto.MetricasRegiaoDTO;
import com.recode.hanami.entities.Venda;
import com.recode.hanami.repository.VendaRepository;
import com.recode.hanami.service.CalculadoraMetricasService;
import com.recode.hanami.service.CalculosDemografiaRegiao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("hanami/reports")
@Tag(name = "Relatórios e Análises", description = "Endpoints para geração de relatórios e análises de vendas")
public class ReportsController {

    private static final Logger logger = LoggerFactory.getLogger(ReportsController.class);

    private final VendaRepository vendaRepository;
    private final CalculadoraMetricasService calculadoraService;
    private final CalculosDemografiaRegiao calculosDemografiaRegiao;

    public ReportsController(VendaRepository vendaRepository,
                             CalculadoraMetricasService calculadoraService,
                             CalculosDemografiaRegiao calculosDemografiaRegiao) {
        this.vendaRepository = vendaRepository;
        this.calculadoraService = calculadoraService;
        this.calculosDemografiaRegiao = calculosDemografiaRegiao;
    }

    @Operation(
            summary = "Métricas financeiras globais",
            description = "Retorna um resumo consolidado das principais métricas financeiras da empresa: " +
                    "receita líquida total, custo total operacional e lucro bruto. " +
                    "Este endpoint processa todas as transações de vendas e calcula os indicadores-chave de desempenho (KPIs) financeiros."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Métricas calculadas com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Exemplo de resposta",
                                    description = "Resumo financeiro consolidado de todas as transações",
                                    value = """
                                            {
                                              "receita_liquida": 458900.75,
                                              "custo_total": 321230.50,
                                              "lucro_bruto": 137670.25
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "200",
                    description = "Sem dados disponíveis",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Resposta sem vendas",
                                    description = "Quando não há transações registradas no sistema",
                                    value = """
                                            {
                                              "receita_liquida": 0.0,
                                              "custo_total": 0.0,
                                              "lucro_bruto": 0.0
                                            }
                                            """
                            )
                    )
            )
    })
    @GetMapping("/financial-metrics")
    public ResponseEntity<Map<String, Double>> getFinancialMetrics() {
        logger.debug("Iniciando cálculo das métricas financeiras gerais");

        List<Venda> vendas = vendaRepository.findAll();

        Double receitaLiquida = calculadoraService.calcularTotalVendas(vendas);
        Double custoTotal = calculadoraService.calcularCustoTotalGeral(vendas);
        Double lucroBruto = calculadoraService.calcularLucroBrutoGeral(vendas);

        Map<String, Double> metrics = new LinkedHashMap<>();
        metrics.put("receita_liquida", receitaLiquida);
        metrics.put("custo_total", custoTotal);
        metrics.put("lucro_bruto", lucroBruto);

        return ResponseEntity.ok(metrics);
    }

    @Operation(
            summary = "Análise detalhada por produto",
            description = """
                    Retorna uma análise agregada das vendas agrupadas por produto.
                    
                    **Campos retornados:**
                    - `nome_produto`: Nome do produto
                    - `quantidade_vendida`: Total de unidades vendidas do produto
                    - `total_arrecadado`: Valor total de receita gerado pelo produto
                    
                    **Parâmetros de ordenação disponíveis:**
                    - `nome` (padrão) - Por nome do produto (alfabético)
                    - `quantidade` - Por quantidade vendida (maior → menor)
                    - `total` - Por total arrecadado (maior → menor)
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Análise realizada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Exemplo de resposta",
                                    description = "Lista de produtos com totais agregados",
                                    value = """
                                            [
                                              {
                                                "nome_produto": "Notebook Dell Inspiron",
                                                "quantidade_vendida": 125,
                                                "total_arrecadado": 400000.00
                                              },
                                              {
                                                "nome_produto": "Mouse Logitech MX Master",
                                                "quantidade_vendida": 350,
                                                "total_arrecadado": 98000.00
                                              }
                                            ]
                                            """
                            )
                    )
            )
    })
    @GetMapping("/product-analysis")
    public ResponseEntity<List<Map<String, Object>>> analisarLucros(
            @Parameter(
                    description = """
                            Critério de ordenação dos resultados. **Opções disponíveis:**
                            
                            - `nome` (padrão) - Ordena por nome do produto (alfabético)
                            - `quantidade` - Ordena por quantidade vendida (maior para menor)
                            - `total` - Ordena por total arrecadado (maior para menor)
                            
                            **Exemplo de uso:** `/hanami/reports/product-analysis?sort_by=total`""",
                    example = "total"
            )
            @RequestParam(value = "sort_by", required = false, defaultValue = "nome") String sortBy
    ) {
        logger.debug("Iniciando análise agregada por produto");

        List<Venda> todasVendas = vendaRepository.findAllWithRelations();

        Map<String, Map<String, Object>> produtosMap = new LinkedHashMap<>();

        for (Venda venda : todasVendas) {
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

        List<Map<String, Object>> relatorio = new ArrayList<>(produtosMap.values());


        if (sortBy != null) {
            switch (sortBy.toLowerCase()) {
                case "quantidade":
                    relatorio.sort((a, b) -> ((Integer) b.get("quantidade_vendida")).compareTo((Integer) a.get("quantidade_vendida")));
                    break;
                case "total":
                    relatorio.sort((a, b) -> ((Double) b.get("total_arrecadado")).compareTo((Double) a.get("total_arrecadado")));
                    break;
                default:
                    relatorio.sort(Comparator.comparing(a -> ((String) a.get("nome_produto"))));
                    break;
            }
        }

        return ResponseEntity.ok(relatorio);
    }

    @Operation(
            summary = "Resumo financeiro das vendas",
            description = "Retorna um resumo detalhado das vendas com métricas de transações, " +
                    "formas de pagamento e canais de venda mais e menos utilizados."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Resumo calculado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Exemplo de resposta",
                                    value = """
                                            {
                                              "numero_total_vendas": 356,
                                              "valor_medio_por_transacao": 690.45,
                                              "forma_pagamento_mais_utilizada": "Cartão de Crédito",
                                              "forma_pagamento_menos_utilizada": "Boleto",
                                              "canal_vendas_mais_utilizado": "E-commerce",
                                              "canal_vendas_menos_utilizado": "Telefone"
                                            }
                                            """
                            )
                    )
            )
    })
    @GetMapping("/sales-summary")
    public ResponseEntity<Map<String, Object>> resumoFinanceiro() {
        logger.debug("Iniciando cálculo do resumo financeiro das vendas");

        List<Venda> todasVendas = vendaRepository.findAll();

        Integer numeroTotalVendas = calculadoraService.calcularNumeroTransacoes(todasVendas);
        Double valorMedioPorTransacao = calculadoraService.calcularMediaPorTransacao(todasVendas);
        String formaPagamentoMaisUtilizada = calculadoraService.calcularFormaPagamentoMaisUtilizada(todasVendas);
        String formaPagamentoMenosUtilizada = calculadoraService.calcularFormaPagamentoMenosUtilizada(todasVendas);
        String canalVendasMaisUtilizado = calculadoraService.calcularCanalVendasMaisUtilizado(todasVendas);
        String canalVendasMenosUtilizado = calculadoraService.calcularCanalVendasMenosUtilizado(todasVendas);

        Map<String, Object> resumo = new LinkedHashMap<>();
        resumo.put("numero_total_vendas", numeroTotalVendas);
        resumo.put("valor_medio_por_transacao", valorMedioPorTransacao);
        resumo.put("forma_pagamento_mais_utilizada", formaPagamentoMaisUtilizada);
        resumo.put("forma_pagamento_menos_utilizada", formaPagamentoMenosUtilizada);
        resumo.put("canal_vendas_mais_utilizado", canalVendasMaisUtilizado);
        resumo.put("canal_vendas_menos_utilizado", canalVendasMenosUtilizado);

        logger.info("Resumo financeiro calculado: {} vendas, média de R$ {}", numeroTotalVendas, valorMedioPorTransacao);

        return ResponseEntity.ok(resumo);
    }

    @Operation(
            summary = "Desempenho por região",
            description = "Retorna métricas de vendas agrupadas por região geográfica. " +
                    "Para cada região, são calculados: total de transações, receita total, " +
                    "quantidade de produtos vendidos e valor médio por transação."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Métricas por região calculadas com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Exemplo de resposta",
                                    description = "Desempenho consolidado por região",
                                    value = """
                                            {
                                              "Sudeste": {
                                                "totalTransacoes": 4523,
                                                "receitaTotal": 1250300.50,
                                                "quantidadeVendida": 8900,
                                                "mediaValorTransacao": 276.42
                                              },
                                              "Sul": {
                                                "totalTransacoes": 2156,
                                                "receitaTotal": 680200.00,
                                                "quantidadeVendida": 4350,
                                                "mediaValorTransacao": 315.52
                                              },
                                              "Nordeste": {
                                                "totalTransacoes": 1890,
                                                "receitaTotal": 450780.25,
                                                "quantidadeVendida": 3200,
                                                "mediaValorTransacao": 238.50
                                              },
                                              "Norte": {
                                                "totalTransacoes": 980,
                                                "receitaTotal": 245600.75,
                                                "quantidadeVendida": 1650,
                                                "mediaValorTransacao": 250.61
                                              },
                                              "Centro-Oeste": {
                                                "totalTransacoes": 751,
                                                "receitaTotal": 198450.00,
                                                "quantidadeVendida": 1200,
                                                "mediaValorTransacao": 264.25
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "200",
                    description = "Sem dados disponíveis",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Resposta vazia",
                                    description = "Quando não há transações com região definida",
                                    value = "{}"
                            )
                    )
            )
    })
    @GetMapping("/regional-performance")
    public ResponseEntity<Map<String, MetricasRegiaoDTO>> getRegionalPerformance() {
        logger.debug("Iniciando cálculo de desempenho por região");

        List<Venda> todasVendas = vendaRepository.findAll();
        Map<String, MetricasRegiaoDTO> metricasPorRegiao =
                calculosDemografiaRegiao.calcularMetricasPorRegiao(todasVendas);

        logger.info("Desempenho por região calculado: {} regiões encontradas", metricasPorRegiao.size());

        return ResponseEntity.ok(metricasPorRegiao);
    }

    @Operation(
            summary = "Perfil demográfico dos clientes",
            description = "Retorna a distribuição dos clientes por gênero, faixa etária e cidade. " +
                    "Para cada categoria, são apresentadas a contagem e o percentual do total."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Perfil demográfico calculado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Exemplo de resposta",
                                    description = "Distribuições demográficas consolidadas",
                                    value = """
                                            {
                                              "por_genero": {
                                                "M": {
                                                  "contagem": 5230,
                                                  "percentual": 52.30
                                                },
                                                "F": {
                                                  "contagem": 4770,
                                                  "percentual": 47.70
                                                }
                                              },
                                              "por_faixa_etaria": {
                                                "26-35": {
                                                  "contagem": 3200,
                                                  "percentual": 32.00
                                                },
                                                "36-45": {
                                                  "contagem": 2800,
                                                  "percentual": 28.00
                                                },
                                                "18-25": {
                                                  "contagem": 2100,
                                                  "percentual": 21.00
                                                },
                                                "46-55": {
                                                  "contagem": 1200,
                                                  "percentual": 12.00
                                                },
                                                "56-65": {
                                                  "contagem": 500,
                                                  "percentual": 5.00
                                                },
                                                "Acima de 65": {
                                                  "contagem": 200,
                                                  "percentual": 2.00
                                                }
                                              },
                                              "por_cidade": {
                                                "São Paulo": {
                                                  "contagem": 2500,
                                                  "percentual": 25.00
                                                },
                                                "Rio de Janeiro": {
                                                  "contagem": 1800,
                                                  "percentual": 18.00
                                                },
                                                "Belo Horizonte": {
                                                  "contagem": 1200,
                                                  "percentual": 12.00
                                                },
                                                "Brasília": {
                                                  "contagem": 950,
                                                  "percentual": 9.50
                                                }
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "200",
                    description = "Sem dados disponíveis",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Resposta vazia",
                                    description = "Quando não há clientes cadastrados",
                                    value = """
                                            {
                                              "por_genero": {},
                                              "por_faixa_etaria": {},
                                              "por_cidade": {}
                                            }
                                            """
                            )
                    )
            )
    })
    @GetMapping("/customer-profile")
    public ResponseEntity<DistribuicaoClientesDTO> getCustomerProfile() {
        logger.debug("Iniciando cálculo de perfil demográfico dos clientes");

        List<Venda> todasVendas = vendaRepository.findAll();
        DistribuicaoClientesDTO distribuicao =
                calculosDemografiaRegiao.calcularDistribuicaoClientes(todasVendas);

        logger.info("Perfil demográfico calculado com sucesso");

        return ResponseEntity.ok(distribuicao);
    }
}

