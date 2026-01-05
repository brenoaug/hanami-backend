package com.recode.hanami.controller;


import com.recode.hanami.dto.DadosArquivoDTO;
import com.recode.hanami.dto.ImportacaoResponseDTO;
import com.recode.hanami.entities.Venda;
import com.recode.hanami.exceptions.ArquivoInvalidoException;
import com.recode.hanami.exceptions.DadosInvalidosException;
import com.recode.hanami.repository.ProdutoRepository;
import com.recode.hanami.repository.VendaRepository;
import com.recode.hanami.service.CalculadoraMetricasService;
import com.recode.hanami.service.CsvService;
import com.recode.hanami.service.ProcessamentoVendasService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hanami")
public class CsvController {
    private static final Logger logger = LoggerFactory.getLogger(CsvController.class);

    private final CsvService csvService;
    private final ProcessamentoVendasService processamentoVendasService;
    private final VendaRepository vendaRepository;
    private final CalculadoraMetricasService calculadoraService;
    //private final ProdutoRepository produtoRepository;

    public CsvController(CsvService csvService,
                         ProcessamentoVendasService processamentoVendasService,
                         VendaRepository vendaRepository,
                         CalculadoraMetricasService calculadoraService,
                         ProdutoRepository produtoRepository) {
        this.csvService = csvService;
        this.processamentoVendasService = processamentoVendasService;
        this.vendaRepository = vendaRepository;
        this.calculadoraService = calculadoraService;
        //this.produtoRepository = produtoRepository;
    }

    @PostMapping("/upload-file")
    public ResponseEntity<?> uploadCsv(@RequestParam(value = "file", required = false) MultipartFile file) {
        logger.info("Iniciando processamento de upload de arquivo");


        if (file == null || file.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "status", "erro",
                            "mensagem", "Nenhum arquivo foi enviado ou o arquivo está vazio."
                    ));
        }

        try {
            logger.info("Iniciando conversão do arquivo CSV para JSON. Nome do arquivo: {}", file.getOriginalFilename());
            List<DadosArquivoDTO> listaProcessada = csvService.conversorCsvParaJson(file);
            logger.debug("Arquivo convertido com sucesso. Número de registros: {}", listaProcessada.size());

            logger.info("Iniciando salvamento dos dados do arquivo");
            processamentoVendasService.salvarDadosDoArquivo(listaProcessada);
            logger.info("Dados salvos com sucesso");


            ImportacaoResponseDTO resposta = new ImportacaoResponseDTO("sucesso", listaProcessada.size());
            logger.info("Processo de upload finalizado com sucesso. Registros processados: {}", listaProcessada.size());

            return ResponseEntity
                    .ok()
                    .body(resposta);

        } catch (ArquivoInvalidoException | DadosInvalidosException e) {
            logger.error("Erro de dados inválidos: {}", e.getMessage());

            return ResponseEntity
                    .unprocessableEntity()
                    .body(Map.of(
                            "status", "erro_processamento",
                            "mensagem", e.getMessage()
                    ));
        } catch (Exception e) {
            logger.error("Erro durante o processamento do arquivo: {}", e.getMessage());
            return ResponseEntity
                    .internalServerError()
                    .body(Map.of(
                            "status", "erro_interno",
                            "mensagem", "Ocorreu um erro inesperado: " + e.getMessage()
                    ));
        }
    }

    @GetMapping("/reports/product-analysis")
    public ResponseEntity<List<Map<String, Object>>> analisarLucros(
            @RequestParam(value = "sort_by", required = false, defaultValue = "id") String sortBy
    ) {
        List<Venda> todasVendas = vendaRepository.findAll();
        List<Map<String, Object>> relatorio = new ArrayList<>();

        for (Venda venda : todasVendas) {
            Double receita = calculadoraService.calcularReceitaLiquida(venda);
            Double custo = calculadoraService.calcularCustoTotalVenda(venda);
            Double lucro = calculadoraService.calcularLucroBruto(venda);

            Map<String, Object> linhaRelatorio = new HashMap<>();
            linhaRelatorio.put("id_transacao", venda.getId());
            linhaRelatorio.put("produto", venda.getProduto().getNomeProduto());
            linhaRelatorio.put("receita_liquida", receita);
            linhaRelatorio.put("custo_estimado", calculadoraService.arredondar(custo));
            linhaRelatorio.put("quantidade_vendida", venda.getQuantidade());
            linhaRelatorio.put("lucro_bruto", lucro);

            double margemNum = (receita > 0) ? (lucro / receita) * 100 : 0.0;

            linhaRelatorio.put("margem_real_valor", margemNum);
            linhaRelatorio.put("margem_real_percent", String.format("%.2f%%", margemNum));

            relatorio.add(linhaRelatorio);
        }

        if (sortBy != null) {
            switch (sortBy.toLowerCase()) {
                case "lucro":
                    relatorio.sort((a, b) -> ((Double) a.get("lucro_bruto")).compareTo((Double) a.get("lucro_bruto")));
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
                    relatorio.sort((a, b) -> ((String) a.get("id_transacao")).compareTo((String) b.get("id_transacao")));
                    break;
            }
        }

        return ResponseEntity.ok(relatorio);
    }

    @GetMapping("/reports/sales-summary")
    public ResponseEntity<Map<String, Object>> resumoFinanceiro() {
        logger.info("Iniciando cálculo do resumo financeiro das vendas");

        List<Venda> todasVendas = vendaRepository.findAll();

        Double totalVendas = calculadoraService.calcularTotalVendas(todasVendas);
        Integer numTransacoes = calculadoraService.calcularNumeroTransacoes(todasVendas);
        Double mediaTransacao = calculadoraService.calcularMediaPorTransacao(todasVendas);

        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("titulo", "Resumo Financeiro Hanami");
        dashboard.put("total_vendas_bruto", totalVendas);
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