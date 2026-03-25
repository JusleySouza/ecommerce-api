package br.com.indra.jusley_freitas.dto.response.history;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "DTO for product price change history. Contains the fields that represent the history of price changes for a product in the system.")
public record HistoryProductResponseDTO(
        @Schema(description = "History identifier", type = "UUID", example = "7494ef92-cbc1-4ed1-bf9b-f9873f916424")
        UUID id,
        @Schema(description = "Product name", type = "String", example = "Geladeira")
        String product,
        @Schema(description = "Product old price", type = "BigDecimal", example = "2.800")
        BigDecimal oldPrice,
        @Schema(description = "Product new price", type = "BigDecimal", example = "3.800")
        BigDecimal newPrice,
        @Schema(description = "Date of change", type = "LocalDateTime", example = "2026-03-23T17:43:24.026Z")
        LocalDateTime dateOfChange
) {}
