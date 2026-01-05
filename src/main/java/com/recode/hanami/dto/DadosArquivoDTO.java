package com.recode.hanami.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.recode.hanami.util.TratamentoDadosUtil;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosArquivoDTO(
        // Vendas
        @JsonProperty("id_transacao")
        String idTransacao,

        @JsonProperty("data_venda")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate dataVenda,

        @JsonProperty("valor_final")
        Double valorFinal,

        @JsonProperty("subtotal")
        Double subtotal,

        @JsonProperty("desconto_percent")
        Double descontoPercent,

        @JsonProperty("canal_venda")
        String canalVenda,

        @JsonProperty("forma_pagamento")
        String formaPagamento,

        // Cliente
        @JsonProperty("cliente_id")
        String clienteId,

        @JsonProperty("nome_cliente")
        String nomeCliente,

        @JsonProperty("idade_cliente")
        Integer idadeCliente,

        @JsonProperty("genero_cliente")
        String generoCliente,

        @JsonProperty("cidade_cliente")
        String cidadeCliente,

        @JsonProperty("estado_cliente")
        String estadoCliente,

        @JsonProperty("renda_estimada")
        Double rendaEstimada,

        // Produto
        @JsonProperty("produto_id")
        String produtoId,

        @JsonProperty("nome_produto")
        String nomeProduto,

        @JsonProperty("categoria")
        String categoria,

        @JsonProperty("marca")
        String marca,

        @JsonProperty("preco_unitario")
        Double precoUnitario,

        @JsonProperty("quantidade")
        Integer quantidade,

        @JsonProperty("margem_lucro")
        Double margemLucro,

        // Logistica e Operacao
        @JsonProperty("regiao")
        String regiao,

        @JsonProperty("status_entrega")
        String statusEntrega,

        @JsonProperty("tempo_entrega_dias")
        Integer tempoEntregaDias,

        @JsonProperty("vendedor_id")
        String vendedorId
) {

    public DadosArquivoDTO {
        // Vendas
        idTransacao = TratamentoDadosUtil.tratarString(idTransacao);
        dataVenda = TratamentoDadosUtil.tratarData(dataVenda);
        valorFinal = TratamentoDadosUtil.tratarDouble(valorFinal);
        subtotal = TratamentoDadosUtil.tratarDouble(subtotal);
        descontoPercent = TratamentoDadosUtil.tratarDouble(descontoPercent);
        canalVenda = TratamentoDadosUtil.tratarString(canalVenda);
        formaPagamento = TratamentoDadosUtil.tratarString(formaPagamento);

        // Cliente
        clienteId = TratamentoDadosUtil.tratarString(clienteId);
        nomeCliente = TratamentoDadosUtil.tratarString(nomeCliente);
        idadeCliente = TratamentoDadosUtil.tratarInteger(idadeCliente);
        generoCliente = TratamentoDadosUtil.tratarGenero(generoCliente);
        cidadeCliente = TratamentoDadosUtil.tratarString(cidadeCliente);
        estadoCliente = TratamentoDadosUtil.tratarString(estadoCliente);
        rendaEstimada = TratamentoDadosUtil.tratarDouble(rendaEstimada);

        // Produto
        produtoId = TratamentoDadosUtil.tratarString(produtoId);
        nomeProduto = TratamentoDadosUtil.tratarString(nomeProduto);
        categoria = TratamentoDadosUtil.tratarString(categoria);
        marca = TratamentoDadosUtil.tratarString(marca);
        precoUnitario = TratamentoDadosUtil.tratarDouble(precoUnitario);
        quantidade = TratamentoDadosUtil.tratarInteger(quantidade);
        margemLucro = TratamentoDadosUtil.tratarDouble(margemLucro);

        // Logistica e Operacao
        regiao = TratamentoDadosUtil.tratarString(regiao);
        statusEntrega = TratamentoDadosUtil.tratarString(statusEntrega);
        tempoEntregaDias = TratamentoDadosUtil.tratarInteger(tempoEntregaDias);
        vendedorId = TratamentoDadosUtil.tratarString(vendedorId);
    }
}