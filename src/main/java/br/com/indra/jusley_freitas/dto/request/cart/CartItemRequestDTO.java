package br.com.indra.jusley_freitas.dto.request.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "DTO for adding an item to the cart. Contains the product ID and the quantity of the product to be added.")
public record CartItemRequestDTO(

        @NotNull(message = "{product.not.null}")
        @Schema(description = "Product identifier", type = "UUID", example = "7494ef92-cbc1-4ed1-bf9b-f9873f916424")
        UUID productId,

        @NotNull(message = "{quantityCartItem.not.null}")
        @Min(value=1, message = "{quantityCartItem.not.less.than}")
        @Schema(description = "Quantity", type = "int", example = "15")
        int quantity
) {}
