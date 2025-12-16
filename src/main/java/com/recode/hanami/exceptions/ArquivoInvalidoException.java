package com.recode.hanami.exceptions;

public class ArquivoInvalidoException extends RuntimeException{
    public ArquivoInvalidoException(String mensagem) {
        super(mensagem);
    }
}
