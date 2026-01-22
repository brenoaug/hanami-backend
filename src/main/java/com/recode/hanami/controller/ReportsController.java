package com.recode.hanami.controller;

import com.recode.hanami.controller.docs.ReportsControllerOpenApi;
import com.recode.hanami.dto.DistribuicaoClientesDTO;
import com.recode.hanami.dto.MetricasRegiaoDTO;
import com.recode.hanami.dto.RelatorioCompletoDTO;
import com.recode.hanami.entities.Venda;
import com.recode.hanami.repository.VendaRepository;
import com.recode.hanami.service.CalculosDemografiaRegiao;
import com.recode.hanami.service.RelatorioService;
import com.recode.hanami.util.DownloadArquivoUtil;
import com.recode.hanami.validation.FormatoRelatorioValidator;
import com.recode.hanami.validation.SortByValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Controller responsável pelos endpoints de relatórios e análises.
 * Implementa apenas orquestração - toda lógica de negócio está nos Services.
 * Exceções são tratadas pelo GlobalExceptionHandler.
 */
@RestController
@RequestMapping("hanami/reports")
public class ReportsController implements ReportsControllerOpenApi {

    private static final Logger logger = LoggerFactory.getLogger(ReportsController.class);

    private final VendaRepository vendaRepository;
    private final CalculosDemografiaRegiao calculosDemografiaRegiao;
    private final RelatorioService relatorioService;
    private final FormatoRelatorioValidator formatoRelatorioValidator;
    private final SortByValidator sortByValidator;

    public ReportsController(VendaRepository vendaRepository,
                             CalculosDemografiaRegiao calculosDemografiaRegiao,
                             RelatorioService relatorioService,
                             FormatoRelatorioValidator formatoRelatorioValidator,
                             SortByValidator sortByValidator) {
        this.vendaRepository = vendaRepository;
        this.calculosDemografiaRegiao = calculosDemografiaRegiao;
        this.relatorioService = relatorioService;
        this.formatoRelatorioValidator = formatoRelatorioValidator;
        this.sortByValidator = sortByValidator;
    }

    @GetMapping("/financial-metrics")
    @Override
    public ResponseEntity<Map<String, Double>> getFinancialMetrics() {
        logger.debug("Solicitação de métricas financeiras");
        Map<String, Double> metrics = relatorioService.gerarMetricasFinanceirasMap();
        return ResponseEntity.ok(metrics);
    }

    @GetMapping("/product-analysis")
    @Override
    public ResponseEntity<List<Map<String, Object>>> analisarLucros(
            @RequestParam(value = "sort_by", required = false, defaultValue = "nome") String sortBy) {
        logger.debug("Solicitação de análise de produtos com ordenação: {}", sortBy);
        String normalizedSortBy = sortByValidator.normalize(sortBy);
        List<Map<String, Object>> relatorio = relatorioService.gerarAnaliseProdutosOrdenada(normalizedSortBy);
        return ResponseEntity.ok(relatorio);
    }

    @GetMapping("/sales-summary")
    @Override
    public ResponseEntity<Map<String, Object>> resumoFinanceiro() {
        logger.debug("Solicitação de resumo de vendas");
        Map<String, Object> resumo = relatorioService.gerarResumoVendasMap();
        logger.info("Resumo de vendas gerado: {} transações", resumo.get("numero_total_vendas"));
        return ResponseEntity.ok(resumo);
    }

    @GetMapping("/regional-performance")
    @Override
    public ResponseEntity<Map<String, MetricasRegiaoDTO>> getRegionalPerformance() {
        logger.debug("Solicitação de desempenho por região");
        List<Venda> vendas = vendaRepository.findAll();
        Map<String, MetricasRegiaoDTO> metricas = calculosDemografiaRegiao.calcularMetricasPorRegiao(vendas);
        logger.info("Desempenho regional calculado: {} regiões", metricas.size());
        return ResponseEntity.ok(metricas);
    }

    @GetMapping("/customer-profile")
    @Override
    public ResponseEntity<DistribuicaoClientesDTO> getCustomerProfile() {
        logger.debug("Solicitação de perfil demográfico");
        List<Venda> vendas = vendaRepository.findAll();
        DistribuicaoClientesDTO distribuicao = calculosDemografiaRegiao.calcularDistribuicaoClientes(vendas);
        logger.info("Perfil demográfico calculado");
        return ResponseEntity.ok(distribuicao);
    }

    @GetMapping("/download")
    @Override
    public ResponseEntity<byte[]> downloadRelatorio(@RequestParam(value = "format") String format) {
        logger.info("Download de relatório solicitado: formato={}", format);

        // Validar formato (lança exceção se inválido)
        formatoRelatorioValidator.validate(format);

        // Gerar relatório completo
        RelatorioCompletoDTO relatorio = relatorioService.gerarRelatorioCompleto();

        // Gerar arquivo conforme formato
        byte[] conteudo = format.equalsIgnoreCase("json")
            ? relatorioService.gerarRelatorioJsonBytes(relatorio)
            : relatorioService.gerarRelatorioPdfBytes(relatorio);

        logger.info("Relatório {} gerado com sucesso - {} bytes", format.toUpperCase(), conteudo.length);

        // Retornar resposta de download configurada
        return DownloadArquivoUtil.buildDownloadResponse(conteudo, format);
    }
}
