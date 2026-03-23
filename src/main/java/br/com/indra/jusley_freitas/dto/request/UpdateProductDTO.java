package br.com.indra.jusley_freitas.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for updating a product. Contains the fields that can be updated for a product in the system.")
public record UpdateProductDTO(
        @Schema(description = "Product name", type = "String", example = "Geladeira")
        String name,
        @Schema(description = "Product description", type = "String", example = "Brastemp")
        String description,
        @Schema(description = "Product stock", type = "int", example = "15")
        int stockQuantity
) {}
