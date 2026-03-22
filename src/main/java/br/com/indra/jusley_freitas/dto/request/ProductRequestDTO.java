package br.com.indra.jusley_freitas.dto.request;

import java.math.BigDecimal;

public record ProductRequestDTO(
        String name,
        String description,
        String sku,
        BigDecimal price,
        BigDecimal costPrice,
        int stockQuantity
) {}
