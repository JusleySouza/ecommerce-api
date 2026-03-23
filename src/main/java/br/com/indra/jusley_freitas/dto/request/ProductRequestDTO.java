package br.com.indra.jusley_freitas.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "DTO for creating a new product. Contains all necessary fields to create a product in the system.")
public record ProductRequestDTO(
        @Schema(description = "Product name", type = "String", example = "Geladeira")
        String name,
        @Schema(description = "Product description", type = "String", example = "Brastemp")
        String description,
        @Schema(description = "Product sku", type = "String", example = "123456")
        String sku,
        @Schema(description = "Product price", type = "BigDecimal", example = "4.500")
        BigDecimal price,
        @Schema(description = "Product cost price", type = "BigDecimal", example = "3.300")
        BigDecimal costPrice,
        @Schema(description = "Product stock", type = "int", example = "15")
        int stockQuantity
) {}
