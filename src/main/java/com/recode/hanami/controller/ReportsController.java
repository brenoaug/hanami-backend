package com.recode.hanami.controller;

import com.recode.hanami.entities.Venda;
import com.recode.hanami.repository.VendaRepository;
import com.recode.hanami.service.CalculadoraMetricasService;
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
public class ReportsController {

    private static final Logger logger = LoggerFactory.getLogger(ReportsController.class);

    private final VendaRepository vendaRepository;
    private final CalculadoraMetricasService calculadoraService;

    // Injeção de dependência via construtor
    public ReportsController(VendaRepository vendaRepository,
                             CalculadoraMetricasService calculadoraService) {
        this.vendaRepository = vendaRepository;
        this.calculadoraService = calculadoraService;
    }

    @GetMapping("/financial-metrics")
    public ResponseEntity<Map<String, Double>> getFinancialMetrics() {
        logger.debug("Iniciando cálculo das métricas financeiras gerais");

        List<Venda> vendas = vendaRepository.findAll();

        Double receitaLiquida = calculadoraService.calcularTotalVendas(vendas);
        Double custoTotal = calculadoraService.calcularCustoTotalGeral(vendas);
        Double lucroBruto = calculadoraService.calcularLucroBrutoGeral(vendas);

        Map<String, Double> metrics = new HashMap<>();
        metrics.put("receita_liquida", receitaLiquida);
        metrics.put("custo_total", custoTotal);
        metrics.put("lucro_bruto", lucroBruto);

        return ResponseEntity.ok(metrics);
    }
    @GetMapping("/product-analysis")
    public ResponseEntity<List<Map<String, Object>>> analisarLucros(
            @RequestParam(value = "sort_by", required = false, defaultValue = "id") String sortBy
    ) {
        logger.debug("Iniciando análise de lucros por produto");

        List<Venda> todasVendas = vendaRepository.findAll();
        List<Map<String, Object>> relatorio = new ArrayList<>();

        for (Venda venda : todasVendas) {
            Double receita = calculadoraService.calcularReceitaLiquida(venda);
            Double custo = calculadoraService.calcularCustoTotalVenda(venda);
            Double lucro = calculadoraService.calcularLucroBruto(venda);

            double margemNum = (receita != null && receita > 0) ? (lucro / receita) * 100 : 0.0;

            Map<String, Object> linhaRelatorio = new HashMap<>();
            linhaRelatorio.put("id_transacao", venda.getId());
            linhaRelatorio.put("nome_produto", venda.getProduto().getNomeProduto());
            linhaRelatorio.put("receita_liquida", receita);
            linhaRelatorio.put("custo_estimado", calculadoraService.arredondar(custo));
            linhaRelatorio.put("quantidade_vendida", venda.getQuantidade());
            linhaRelatorio.put("lucro_bruto", lucro);
            linhaRelatorio.put("margem_real_valor", margemNum);
            linhaRelatorio.put("margem_real_percent", String.format("%.2f%%", margemNum));

            relatorio.add(linhaRelatorio);
        }

        if (sortBy != null) {
            switch (sortBy.toLowerCase()) {
                case "lucro":
                    relatorio.sort((a, b) -> ((Double) b.get("lucro_bruto")).compareTo((Double) a.get("lucro_bruto")));
                    break;
                case "receita":
                    relatorio.sort((a, b) -> ((Double) b.get("receita_liquida")).compareTo((Double) a.get("receita_liquida")));
                    break;
                case "margem":
                    relatorio.sort((a, b) -> ((Double) b.get("margem_real_valor")).compareTo((Double) a.get("margem_real_valor")));
                    break;
                case "custo":
                    relatorio.sort((a, b) -> ((Double) b.get("custo_estimado")).compareTo((Double) a.get("custo_estimado")));
                    break;
                case "quantidade":
                    relatorio.sort((a, b) -> ((Integer) b.get("quantidade_vendida")).compareTo((Integer) a.get("quantidade_vendida")));
                    break;
                default:
                    relatorio.sort(Comparator.comparing(a -> ((String) a.get("id_transacao"))));
                    break;
            }
        }

        return ResponseEntity.ok(relatorio);
    }

    @GetMapping("/sales-summary")
    public ResponseEntity<Map<String, Object>> resumoFinanceiro() {
        logger.debug("Iniciando cálculo do resumo financeiro das vendas");

        List<Venda> todasVendas = vendaRepository.findAll();

        Double totalVendas = calculadoraService.calcularTotalVendas(todasVendas);
        Integer numTransacoes = calculadoraService.calcularNumeroTransacoes(todasVendas);
        Double mediaTransacao = calculadoraService.calcularMediaPorTransacao(todasVendas);

        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("titulo", "Resumo Financeiro Hanami");
        dashboard.put("total_vendas", totalVendas);
        dashboard.put("numero_transacoes", numTransacoes);
        dashboard.put("media_por_transacoes", mediaTransacao);

        return ResponseEntity.ok(dashboard);
    }
//    @GetMapping("/produtos")
//    public ResponseEntity<List<Produto>> listarProdutos(
//            @RequestParam(name = "sort_by", required = false) String sortBy) {
//
//        if (sortBy == null) {
//            return ResponseEntity.ok(produtoRepository.findAll());
//        }
//
//        String campoParaOrdenar;
//        Sort.Direction direcao = Sort.Direction.ASC;
//
//        switch (sortBy) {
//            case "quantidade":
//                campoParaOrdenar = "quantidade";
//                direcao = Sort.Direction.DESC;
//                break;
//            case "preco":
//                campoParaOrdenar = "precoUnitario";
//                direcao = Sort.Direction.DESC;
//                break;
//            case "nome":
//                campoParaOrdenar = "nomeProduto";
//                break;
//            case "margem":
//                campoParaOrdenar = "margemLucro";
//                direcao = Sort.Direction.DESC;
//                break;
//            default:
//                campoParaOrdenar = "nomeProduto";
//        }
//
//        Sort ordenacao = Sort.by(direcao, campoParaOrdenar);
//
//        return ResponseEntity.ok(produtoRepository.findAll(ordenacao));
//    }
}