package com.recode.hanami.util;

import java.time.LocalDate;

public class TratamentoDadosUtil {
    private TratamentoDadosUtil(){}

    public static String tratarString(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return "não informado/não cadastrado";
        }
        return valor.trim().toLowerCase();
    }

    public static Integer tratarInteger(Integer valor) {
        return (valor == null) ? 0 : valor;
    }

    public static Double tratarDouble(Double valor) {
        return (valor == null) ? 0.0 : valor;
    }

    public static String tratarGenero(String valor) {
        return (valor == null) ? "não especificado" : valor;
    }

    public static LocalDate tratarData(LocalDate valor) {
        if(valor == null)
            return LocalDate.of(1900, 1, 1);
        return valor;
    }
}
