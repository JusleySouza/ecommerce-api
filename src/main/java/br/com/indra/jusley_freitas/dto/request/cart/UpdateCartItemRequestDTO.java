package br.com.indra.jusley_freitas.dto.request.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO for updating the quantity of an item in the cart. Contains the new quantity of the product.")
public record UpdateCartItemRequestDTO(
        @NotNull(message = "{quantityCartItem.not.null}")
        @Min(value=1, message = "{quantityCartItem.not.less.than}")
        @Schema(description = "Quantity", type = "int", example = "15")
        int quantity
) {}
