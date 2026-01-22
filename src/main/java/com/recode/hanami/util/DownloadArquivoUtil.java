package com.recode.hanami.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public final class DownloadArquivoUtil {

    private DownloadArquivoUtil() {
        throw new UnsupportedOperationException("Utility class - não pode ser instanciada");
    }

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

    public static ResponseEntity<byte[]> buildDownloadResponse(byte[] data, String format) {
        MediaType mediaType = resolveMediaType(format);
        String filename = resolveFilename(format);
        return buildResponse(data, filename, mediaType);
    }

    private static MediaType resolveMediaType(String format) {
        return format.equalsIgnoreCase("json")
            ? MediaType.APPLICATION_JSON
            : MediaType.APPLICATION_PDF;
    }

    private static String resolveFilename(String format) {
        return format.equalsIgnoreCase("json")
            ? "report.json"
            : "report.pdf";
    }
}
