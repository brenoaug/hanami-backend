package com.recode.hanami.exception;

public class ArquivoInvalidoException extends RuntimeException{
    public ArquivoInvalidoException(String mensagem) {
        super(mensagem);
    }
}
