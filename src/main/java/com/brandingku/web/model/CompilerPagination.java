package com.brandingku.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompilerPagination {
    private int pages;
    private int limit;
    private String sortBy;
    private String direction;
    private String keyword;
}
