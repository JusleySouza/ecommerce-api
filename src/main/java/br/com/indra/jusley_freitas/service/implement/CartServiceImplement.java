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

    public CartResponseDTO getCart(UUID userId) {
        Cart cart = getOrCreateCart(userId);

        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());

        LoggerConfig.LOGGER_CART.info("Cart successfully executed!");
        return CartMapper.toResponse(cart, items);
    }

    public CartResponseDTO addItem(UUID userId, CartItemRequestDTO request) {
        Cart cart = getOrCreateCart(userId);

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new ResourceNotFoundException("We were unable to find a product with this ID: " + request.productId()));

        Optional<CartItem> existingItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), request.productId());

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.quantity());
            cartItemRepository.save(item);
        } else {
            CartItem newItem = CartMapper.toItemModel(cart.getId(), product, request);
            cartItemRepository.save(newItem);
        }

        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());

        LoggerConfig.LOGGER_CART.info("Item added to cart successfully!");
        return CartMapper.toResponse(cart, items);
    }

    public void updateItem(UUID userId, UUID productId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        CartItem item = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found in cart: " + productId));

        item.setQuantity(quantity);

        LoggerConfig.LOGGER_CART.info("Item updated to cart successfully!");
        cartItemRepository.save(item);
    }

    public void removeItem(UUID userId, UUID productId) {
        Cart cart = getOrCreateCart(userId);

        CartItem item = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found to cart: "+ productId));

        LoggerConfig.LOGGER_CART.info("Item removed to cart successfully!");
        cartItemRepository.delete(item);
    }


}
