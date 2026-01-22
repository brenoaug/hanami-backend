package com.recode.hanami.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * Classe utilitária para construção de respostas HTTP de download de arquivos.
 * Encapsula a lógica de configuração de headers (Content-Type, Content-Disposition, Content-Length).
 *
 * Utility Class: construtor privado, métodos estáticos, final.
 */
public final class DownloadArquivoUtil {

    private DownloadArquivoUtil() {
        throw new UnsupportedOperationException("Utility class - não pode ser instanciada");
    }

    /**
     * Constrói ResponseEntity configurado para download de arquivo.
     *
     * @param data Bytes do arquivo
     * @param filename Nome do arquivo para download
     * @param mediaType Tipo MIME do arquivo
     * @return ResponseEntity configurado com headers apropriados
     */
    public static ResponseEntity<byte[]> buildResponse(byte[] data, String filename, MediaType mediaType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        headers.setContentDispositionFormData("attachment", filename);
        headers.setContentLength(data.length);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(data);
    }

    /**
     * Constrói ResponseEntity para download baseado no formato.
     * Resolve automaticamente o MediaType e o filename.
     *
     * @param data Bytes do arquivo
     * @param format Formato do arquivo ("json" ou "pdf")
     * @return ResponseEntity configurado
     */
    public static ResponseEntity<byte[]> buildDownloadResponse(byte[] data, String format) {
        MediaType mediaType = resolveMediaType(format);
        String filename = resolveFilename(format);
        return buildResponse(data, filename, mediaType);
    }

    /**
     * Resolve o MediaType baseado no formato.
     *
     * @param format Formato do arquivo ("json" ou "pdf")
     * @return MediaType correspondente
     */
    private static MediaType resolveMediaType(String format) {
        return format.equalsIgnoreCase("json")
            ? MediaType.APPLICATION_JSON
            : MediaType.APPLICATION_PDF;
    }

    /**
     * Resolve o nome do arquivo baseado no formato.
     *
     * @param format Formato do arquivo ("json" ou "pdf")
     * @return Nome do arquivo com extensão
     */
    private static String resolveFilename(String format) {
        return format.equalsIgnoreCase("json")
            ? "report.json"
            : "report.pdf";
    }
}
