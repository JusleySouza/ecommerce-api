package br.com.indra.jusley_freitas.service.implement;

import br.com.indra.jusley_freitas.config.LoggerConfig;
import br.com.indra.jusley_freitas.dto.request.cart.CartItemRequestDTO;
import br.com.indra.jusley_freitas.dto.response.cart.CartResponseDTO;
import br.com.indra.jusley_freitas.enums.CartStatus;
import br.com.indra.jusley_freitas.exception.ResourceNotFoundException;
import br.com.indra.jusley_freitas.mapper.CartMapper;
import br.com.indra.jusley_freitas.model.Cart;
import br.com.indra.jusley_freitas.model.CartItem;
import br.com.indra.jusley_freitas.model.Product;
import br.com.indra.jusley_freitas.repository.CartItemRepository;
import br.com.indra.jusley_freitas.repository.CartRepository;
import br.com.indra.jusley_freitas.repository.ProductRepository;
import br.com.indra.jusley_freitas.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional
public class CartServiceImplement implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    private Cart getOrCreateCart(UUID userId) {
        return cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseGet(() -> {
                    Cart cart = CartMapper.toModel(userId);
                    return cartRepository.save(cart);
                });
    }

    @Override
    public CartResponseDTO getCart(UUID userId) {
        Cart cart = getOrCreateCart(userId);

        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());

        LoggerConfig.LOGGER_CART.info("Cart successfully executed!");
        return CartMapper.toResponse(cart, items);
    }

}
