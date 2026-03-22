package br.com.indra.jusley_freitas.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductResponseDTO(
        UUID id,
        String name,
        String description,
        String sku,
        BigDecimal price,
        BigDecimal costPrice,
        int  stockQuantity,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
