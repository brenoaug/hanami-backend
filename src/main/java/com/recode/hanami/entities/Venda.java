package com.recode.hanami.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vendas")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Venda {

    @Id
    @Column(name = "id_transacao")
    private String id;

    @Column(name = "data_venda")
    private LocalDate dataVenda;

    @Column(name = "valor_final")
    private Double valorFinal;

    @Column(name = "subtotal")
    private Double subtotal;

    @Column(name = "desconto_percent")
    private Double descontoPercent;

    @Column(name = "canal_venda")
    private String canalVenda;

    @Column(name = "forma_pagamento")
    private String formaPagamento;


    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "regiao")
    private String regiao;

    @Column(name = "status_entrega")
    private String statusEntrega;

    @Column(name = "tempo_entrega_dias")
    private Integer tempoEntregaDias;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendedor_id")
    private Vendedor vendedor;

    public Venda() {
    }


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public LocalDate getDataVenda() { return dataVenda; }
    public void setDataVenda(LocalDate dataVenda) { this.dataVenda = dataVenda; }

    public Double getValorFinal() { return valorFinal; }
    public void setValorFinal(Double valorFinal) { this.valorFinal = valorFinal; }

    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }

    public Double getDescontoPercent() { return descontoPercent; }
    public void setDescontoPercent(Double descontoPercent) { this.descontoPercent = descontoPercent; }

    public String getCanalVenda() { return canalVenda; }
    public void setCanalVenda(String canalVenda) { this.canalVenda = canalVenda; }

    public String getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(String formaPagamento) { this.formaPagamento = formaPagamento; }

    // --- GETTERS E SETTERS DOS NOVOS CAMPOS (ESSENCIAIS PARA O ERRO SUMIR) ---

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public String getRegiao() { return regiao; }
    public void setRegiao(String regiao) { this.regiao = regiao; }

    public String getStatusEntrega() { return statusEntrega; }
    public void setStatusEntrega(String statusEntrega) { this.statusEntrega = statusEntrega; }

    public Integer getTempoEntregaDias() { return tempoEntregaDias; }
    public void setTempoEntregaDias(Integer tempoEntregaDias) { this.tempoEntregaDias = tempoEntregaDias; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }

    public Vendedor getVendedor() { return vendedor; }
    public void setVendedor(Vendedor vendedor) { this.vendedor = vendedor; }
}