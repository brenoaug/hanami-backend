package com.recode.hanami.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cliente {

    @Id
    @Column(name = "cliente_id")
    private String id;

    @Column(name = "nome_cliente")
    private String nomeCliente;

    @Column(name = "idade_cliente")
    private Integer idadeCliente;

    @Column(name = "genero_cliente", length = 10)
    private String generoCliente;

    @Column(name = "cidade_cliente")
    private String cidadeCliente;

    @Column(name = "estado_cliente", length = 2)
    private String estadoCliente;

    @Column(name = "renda_estimada")
    private double rendaEstimada;

    public Cliente() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public Integer getIdadeCliente() {
        return idadeCliente;
    }

    public void setIdadeCliente(Integer idadeCliente) {
        this.idadeCliente = idadeCliente;
    }

    public String getGeneroCliente() {
        return generoCliente;
    }

    public void setGeneroCliente(String generoCliente) {
        this.generoCliente = generoCliente;
    }

    public String getCidadeCliente() {
        return cidadeCliente;
    }

    public void setCidadeCliente(String cidadeCliente) {
        this.cidadeCliente = cidadeCliente;
    }

    public String getEstadoCliente() {
        return estadoCliente;
    }

    public void setEstadoCliente(String estadoCliente) {
        this.estadoCliente = estadoCliente;
    }

    public double getRendaEstimada() {
        return rendaEstimada;
    }

    public void setRendaEstimada(double rendaEstimada) {
        this.rendaEstimada = rendaEstimada;
    }
}