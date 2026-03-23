package br.com.indra.jusley_freitas.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "DTO for product response. Contains all fields that represent a product in the system.")
public record ProductResponseDTO(
        @Schema(description = "History identifier", type = "UUID", example = "7494ef92-cbc1-4ed1-bf9b-f9873f916424")
        UUID id,
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
        int  stockQuantity,
        @Schema(description = "Product is active", type = "boolean", example = "true")
        Boolean active,
        @Schema(description = "create date", type = "LocalDateTime", example = "2026-03-23T17:43:24.026Z")
        LocalDateTime createdAt,
        @Schema(description = "update date", type = "LocalDateTime", example = "2026-03-23T17:43:24.026Z")
        LocalDateTime updatedAt
) {}
