package com.recode.hanami.validation;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class SortByValidator {

    private static final List<String> VALID_SORT_OPTIONS = Arrays.asList("nome", "quantidade", "total");
    private static final String DEFAULT_SORT = "nome";

    public boolean isValid(String sortBy) {
        if (sortBy == null || sortBy.isBlank()) {
            return true;
        }
        return VALID_SORT_OPTIONS.contains(sortBy.toLowerCase());
    }

    public String normalize(String sortBy) {
        if (sortBy == null || sortBy.isBlank()) {
            return DEFAULT_SORT;
        }
        return sortBy.toLowerCase();
    }

    public String getDefaultSort() {
        return DEFAULT_SORT;
    }

    public List<String> getValidSortOptions() {
        return VALID_SORT_OPTIONS;
    }
}
