package br.com.indra.jusley_freitas.dto.response.inventory;

import br.com.indra.jusley_freitas.enums.InventoryReason;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "DTO for inventory response. Contains all fields that represent an inventory transaction in the system.")
public record CompleteInventoryTransactionResponseDTO(
        @Schema(description = "inventory identifier", type = "UUID", example = "7494ef92-cbc1-4ed1-bf9b-f9873f916424")
        UUID id,
        @Schema(description = "product identifier", type = "UUID", example = "7494ef92-cbc1-4ed1-bf9b-f9873f916424")
        UUID productId,
        @Schema(description = "delta", type = "int", example = "4")
        Integer delta,
        @Schema(description = "transaction reason", type = "String", example = "PURCHASE")
        InventoryReason reason,
        @Schema(description = "reference identifier", type = "String", example = "7494ef92-cbc1-4ed1-bf9b-f9873f916424")
        String referenceID,
        @Schema(description = "created by", type = "String", example = "Lara Lins")
        String createdBy,
        @Schema(description = "create date", type = "LocalDateTime", example = "2026-03-23T17:43:24.026Z")
        LocalDateTime createdAt
) {}
