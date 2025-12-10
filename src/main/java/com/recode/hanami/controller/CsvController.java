package com.recode.hanami.controller;

import com.recode.hanami.service.CsvService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/csv")
public class CsvController {

    @Autowired
    private CsvService csvService;

    @PostMapping("/upload")
    public List<Map<String, String>> uploadCsv(@RequestParam("file") MultipartFile file) throws IOException {
        return csvService.convertCsvToJson(file.getInputStream());
    }
}