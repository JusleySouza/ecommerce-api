package br.com.indra.jusley_freitas.dto.response.cart;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "DTO for cart item response. Contains the fields that represent an item in the shopping cart.")
public record CartItemResponseDTO(
        @Schema(description = "Product identifier", type = "UUID", example = "7494ef92-cbc1-4ed1-bf9b-f9873f916424")
        UUID productId,
        @Schema(description = "Quantity", type = "int", example = "2")
        int quantity,
        @Schema(description = "Product price", type = "BigDecimal", example = "2.800")
        BigDecimal price,
        @Schema(description = "Total", type = "BigDecimal", example = "5.300")
        BigDecimal total
) {}
