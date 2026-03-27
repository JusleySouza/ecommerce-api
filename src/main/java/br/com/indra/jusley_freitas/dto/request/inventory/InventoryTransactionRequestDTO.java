package br.com.indra.jusley_freitas.dto.request.inventory;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO for creating a new inventory. Contains all necessary fields to create an inventory transaction in the system.")
public record InventoryTransactionRequestDTO(
        @NotNull(message = "{quantity.not.null}")
        @Min(value=0, message = "{quantity.not.less.than}")
        @Schema(description = "quantity product", type = "int", example = "4")
        int quantity,
        @NotEmpty(message = "{reason.not.empty}")
        @Schema(description = "transaction reason", type = "String", example = "PURCHASE")
        String reason
) {}
