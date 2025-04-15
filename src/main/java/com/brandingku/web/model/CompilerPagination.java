package com.brandingku.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;

public record CompilerPagination(
        int pages,
        int limit,
        String sortBy,
        String direction,
        String keyword
) {
}
