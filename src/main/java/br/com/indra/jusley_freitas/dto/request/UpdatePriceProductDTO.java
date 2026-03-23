package br.com.indra.jusley_freitas.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "DTO for updating the product price. Contains only the price field to update the product price in the system.")
public record UpdatePriceProductDTO(
        @Schema(description = "Product price", type = "BigDecimal", example = "4.500")
        BigDecimal price
) {}
