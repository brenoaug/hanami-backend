package com.recode.hanami.service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class CsvService {
    public List<Map<String, String>> convertCsvToJson(InputStream archive) throws IOException {
        try {
            CsvMapper csvMapper = new CsvMapper();

            CsvSchema csvSchema = CsvSchema.emptySchema().withHeader();

            MappingIterator<Map<String, String>> it = csvMapper
                    .readerFor(Map.class)
                    .with(csvSchema)
                    .readValues(archive);

            return it.readAll();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar o arquivo CSV: " + e.getMessage());
        }

    }
}