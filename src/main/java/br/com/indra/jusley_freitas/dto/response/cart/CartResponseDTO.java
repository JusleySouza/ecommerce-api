package br.com.indra.jusley_freitas.dto.response.cart;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Schema(description = "DTO for cart response. Contains the fields that represent the shopping cart in the system.")
public record CartResponseDTO(
        @Schema(description = "Cart identifier", type = "UUID", example = "7494ef92-cbc1-4ed1-bf9b-f9873f916424")
        UUID cartId,
        @Schema(description = "List of items in the cart", type = "List<CartItemResponseDTO>")
        List<CartItemResponseDTO> items,
        @Schema(description = "Total", type = "BigDecimal", example = "5.300")
        BigDecimal total
) {}
