package com.recode.hanami.validation;

import com.recode.hanami.exception.DadosInvalidosException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class FormatoRelatorioValidator {

    private static final List<String> VALID_FORMATS = Arrays.asList("json", "pdf");

    public void validate(String format) {
        if (format == null || format.isBlank()) {
            throw new DadosInvalidosException("Formato não especificado. Use 'json' ou 'pdf'.");
        }

        if (!VALID_FORMATS.contains(format.toLowerCase())) {
            throw new DadosInvalidosException("Formato inválido. Use 'json' ou 'pdf'.");
        }
    }

    public List<String> getValidFormats() {
        return VALID_FORMATS;
    }
}
