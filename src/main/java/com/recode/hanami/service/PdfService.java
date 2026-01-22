package com.recode.hanami.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.recode.hanami.dto.AnaliseProdutoDTO;
import com.recode.hanami.dto.MetricasRegiaoDTO;
import com.recode.hanami.dto.RelatorioCompletoDTO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class PdfService {

    private static final Logger logger = LoggerFactory.getLogger(PdfService.class);
    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public byte[] gerarRelatorioPdf(RelatorioCompletoDTO relatorio) throws DocumentException, IOException {
        logger.info("Iniciando geração do relatório em PDF");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, baos);

        document.open();

        adicionarTitulo(document, relatorio);
        adicionarMetricasFinanceiras(document, relatorio);
        adicionarGraficoVendasPorRegiao(document, relatorio);
        adicionarTabelaProdutos(document, relatorio);
        adicionarResumoVendas(document, relatorio);
        adicionarTabelaDesempenhoRegional(document, relatorio);

        document.close();

        logger.info("Relatório PDF gerado com sucesso");
        return baos.toByteArray();
    }

    private void adicionarTitulo(Document document, RelatorioCompletoDTO relatorio) throws DocumentException {
        com.lowagie.text.Font tituloFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 20, com.lowagie.text.Font.BOLD, Color.DARK_GRAY);
        Paragraph titulo = new Paragraph("Relatório de Análise de Vendas", tituloFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(10);
        document.add(titulo);

        com.lowagie.text.Font dataFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 10, com.lowagie.text.Font.NORMAL, Color.GRAY);
        Paragraph data = new Paragraph("Gerado em: " + relatorio.dataGeracao().format(DATE_FORMAT), dataFont);
        data.setAlignment(Element.ALIGN_CENTER);
        data.setSpacingAfter(20);
        document.add(data);
    }

    private void adicionarMetricasFinanceiras(Document document, RelatorioCompletoDTO relatorio) throws DocumentException {
        com.lowagie.text.Font secaoFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 14, com.lowagie.text.Font.BOLD, Color.BLACK);
        Paragraph secaoTitulo = new Paragraph("1. Métricas Financeiras", secaoFont);
        secaoTitulo.setSpacingBefore(10);
        secaoTitulo.setSpacingAfter(10);
        document.add(secaoTitulo);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingAfter(15);

        adicionarCelulaCabecalho(table, "Métrica");
        adicionarCelulaCabecalho(table, "Valor");

        adicionarCelula(table, "Receita Líquida");
        adicionarCelula(table, CURRENCY_FORMAT.format(relatorio.metricasFinanceiras().receitaLiquida()));

        adicionarCelula(table, "Custo Total");
        adicionarCelula(table, CURRENCY_FORMAT.format(relatorio.metricasFinanceiras().custoTotal()));

        adicionarCelula(table, "Lucro Bruto");
        adicionarCelula(table, CURRENCY_FORMAT.format(relatorio.metricasFinanceiras().lucroBruto()));

        document.add(table);
    }

    private void adicionarGraficoVendasPorRegiao(Document document, RelatorioCompletoDTO relatorio) throws DocumentException, IOException {
        com.lowagie.text.Font secaoFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 14, com.lowagie.text.Font.BOLD, Color.BLACK);
        Paragraph secaoTitulo = new Paragraph("2. Gráfico de Vendas por Região", secaoFont);
        secaoTitulo.setSpacingBefore(10);
        secaoTitulo.setSpacingAfter(10);
        document.add(secaoTitulo);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, MetricasRegiaoDTO> regioes = relatorio.desempenhoRegional();

        for (Map.Entry<String, MetricasRegiaoDTO> entry : regioes.entrySet()) {
            dataset.addValue(entry.getValue().receitaTotal(), "Receita", entry.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Receita Total por Região",
                "Região",
                "Receita (R$)",
                dataset
        );

        chart.setBackgroundPaint(Color.WHITE);
        chart.getPlot().setBackgroundPaint(new Color(240, 240, 240));

        BufferedImage bufferedImage = chart.createBufferedImage(500, 300);
        ByteArrayOutputStream chartBaos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", chartBaos);

        com.lowagie.text.Image chartImage = com.lowagie.text.Image.getInstance(chartBaos.toByteArray());
        chartImage.setAlignment(Element.ALIGN_CENTER);
        chartImage.scaleToFit(500, 300);
        chartImage.setSpacingAfter(15);
        document.add(chartImage);
    }

    private void adicionarTabelaProdutos(Document document, RelatorioCompletoDTO relatorio) throws DocumentException {
        com.lowagie.text.Font secaoFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 14, com.lowagie.text.Font.BOLD, Color.BLACK);
        Paragraph secaoTitulo = new Paragraph("3. Análise de Produtos", secaoFont);
        secaoTitulo.setSpacingBefore(10);
        secaoTitulo.setSpacingAfter(10);
        document.add(secaoTitulo);

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setSpacingAfter(15);

        adicionarCelulaCabecalho(table, "Produto");
        adicionarCelulaCabecalho(table, "Quantidade Vendida");
        adicionarCelulaCabecalho(table, "Total Arrecadado");

        List<AnaliseProdutoDTO> produtos = relatorio.analiseProdutos();
        int limite = Math.min(10, produtos.size());

        for (int i = 0; i < limite; i++) {
            AnaliseProdutoDTO produto = produtos.get(i);
            adicionarCelula(table, produto.nomeProduto());
            adicionarCelula(table, String.valueOf(produto.quantidadeVendida()));
            adicionarCelula(table, CURRENCY_FORMAT.format(produto.totalArrecadado()));
        }

        document.add(table);

        if (produtos.size() > 10) {
            com.lowagie.text.Font notaFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 9, com.lowagie.text.Font.ITALIC, Color.GRAY);
            Paragraph nota = new Paragraph("* Exibindo os 10 produtos mais vendidos de um total de " + produtos.size() + " produtos.", notaFont);
            nota.setSpacingAfter(15);
            document.add(nota);
        }
    }

    private void adicionarResumoVendas(Document document, RelatorioCompletoDTO relatorio) throws DocumentException {
        com.lowagie.text.Font secaoFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 14, com.lowagie.text.Font.BOLD, Color.BLACK);
        Paragraph secaoTitulo = new Paragraph("4. Resumo de Vendas", secaoFont);
        secaoTitulo.setSpacingBefore(10);
        secaoTitulo.setSpacingAfter(10);
        document.add(secaoTitulo);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingAfter(15);

        adicionarCelulaCabecalho(table, "Indicador");
        adicionarCelulaCabecalho(table, "Valor");

        adicionarCelula(table, "Número Total de Vendas");
        adicionarCelula(table, String.valueOf(relatorio.resumoVendas().numeroTotalVendas()));

        adicionarCelula(table, "Valor Médio por Transação");
        adicionarCelula(table, CURRENCY_FORMAT.format(relatorio.resumoVendas().valorMedioPorTransacao()));

        adicionarCelula(table, "Forma de Pagamento Mais Utilizada");
        adicionarCelula(table, relatorio.resumoVendas().formaPagamentoMaisUtilizada());

        adicionarCelula(table, "Forma de Pagamento Menos Utilizada");
        adicionarCelula(table, relatorio.resumoVendas().formaPagamentoMenosUtilizada());

        adicionarCelula(table, "Canal de Vendas Mais Utilizado");
        adicionarCelula(table, relatorio.resumoVendas().canalVendasMaisUtilizado());

        adicionarCelula(table, "Canal de Vendas Menos Utilizado");
        adicionarCelula(table, relatorio.resumoVendas().canalVendasMenosUtilizado());

        document.add(table);
    }

    private void adicionarTabelaDesempenhoRegional(Document document, RelatorioCompletoDTO relatorio) throws DocumentException {
        com.lowagie.text.Font secaoFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 14, com.lowagie.text.Font.BOLD, Color.BLACK);
        Paragraph secaoTitulo = new Paragraph("5. Desempenho Regional Detalhado", secaoFont);
        secaoTitulo.setSpacingBefore(10);
        secaoTitulo.setSpacingAfter(10);
        document.add(secaoTitulo);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingAfter(15);

        adicionarCelulaCabecalho(table, "Região");
        adicionarCelulaCabecalho(table, "Transações");
        adicionarCelulaCabecalho(table, "Receita Total");
        adicionarCelulaCabecalho(table, "Qtd. Vendida");
        adicionarCelulaCabecalho(table, "Média/Transação");

        for (Map.Entry<String, MetricasRegiaoDTO> entry : relatorio.desempenhoRegional().entrySet()) {
            MetricasRegiaoDTO metricas = entry.getValue();
            adicionarCelula(table, entry.getKey());
            adicionarCelula(table, String.valueOf(metricas.totalTransacoes()));
            adicionarCelula(table, CURRENCY_FORMAT.format(metricas.receitaTotal()));
            adicionarCelula(table, String.valueOf(metricas.quantidadeVendida()));
            adicionarCelula(table, CURRENCY_FORMAT.format(metricas.mediaValorTransacao()));
        }

        document.add(table);
    }

    private void adicionarCelulaCabecalho(PdfPTable table, String texto) {
        com.lowagie.text.Font font = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 10, com.lowagie.text.Font.BOLD, Color.WHITE);
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setBackgroundColor(new Color(70, 130, 180));
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }

    private void adicionarCelula(PdfPTable table, String texto) {
        com.lowagie.text.Font font = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 9, com.lowagie.text.Font.NORMAL, Color.BLACK);
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);
    }
}
