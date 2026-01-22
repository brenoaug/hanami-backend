package com.recode.hanami.exception.handler;

/**
 * Enum que define os tipos/categorias de erro retornados pela API.
 * Elimina magic strings e centraliza os tipos de erro em um único lugar.
 */
public enum ErrorType {

    /**
     * Erro de validação de entrada (ex: arquivo vazio, formato inválido).
     */
    ERRO("erro"),

    /**
     * Erro no processamento dos dados (ex: CSV malformado, dados inconsistentes).
     */
    ERRO_PROCESSAMENTO("erro_processamento"),

    /**
     * Erro interno do servidor (ex: falha ao gerar PDF, erro de I/O).
     */
    ERRO_INTERNO("erro_interno");

    private final String value;

    ErrorType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
