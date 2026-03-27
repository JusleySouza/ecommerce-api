package br.com.indra.jusley_freitas.dto.response.inventory;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "DTO for inventory transaction response. " +
        "Contains only the necessary fields to represent the result of an inventory transaction in the system.")
public record InventoryTransactionResponseDTO(
        @Schema(description = "product identifier", type = "UUID", example = "7494ef92-cbc1-4ed1-bf9b-f9873f916424")
        UUID productId,
        @Schema(description = "current stock", type = "int", example = "4")
        int currentStock
) {}
