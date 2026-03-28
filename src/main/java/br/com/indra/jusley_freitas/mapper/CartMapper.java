package br.com.indra.jusley_freitas.mapper;

import br.com.indra.jusley_freitas.dto.request.cart.CartItemRequestDTO;
import br.com.indra.jusley_freitas.dto.response.cart.CartItemResponseDTO;
import br.com.indra.jusley_freitas.dto.response.cart.CartResponseDTO;
import br.com.indra.jusley_freitas.enums.CartStatus;
import br.com.indra.jusley_freitas.model.Cart;
import br.com.indra.jusley_freitas.model.CartItem;
import br.com.indra.jusley_freitas.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class CartMapper {

    public static Cart toModel(UUID userId) {
        return Cart.builder()
                .userId(userId)
                .status(CartStatus.ACTIVE)
                .build();
    }

    public static CartItem toItemModel(UUID cartId, Product product, CartItemRequestDTO dto) {
        return CartItem.builder()
                .cartId(cartId)
                .productId(product.getId())
                .quantity(dto.quantity())
                .priceSnapshot(product.getPrice())
                .build();
    }

    public static CartItemResponseDTO toItemResponse(CartItem item) {
        BigDecimal total = item.getPriceSnapshot()
                .multiply(BigDecimal.valueOf(item.getQuantity()));

        return new CartItemResponseDTO(
                item.getProductId(),
                item.getQuantity(),
                item.getPriceSnapshot(),
                total
        );
    }

    public static CartResponseDTO toResponse(Cart cart, List<CartItem> items) {

        List<CartItemResponseDTO> itemResponses = items.stream()
                .map(CartMapper::toItemResponse)
                .toList();

        BigDecimal total = itemResponses.stream()
                .map(CartItemResponseDTO::total)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartResponseDTO(
                cart.getId(),
                itemResponses,
                total
        );
    }

}
