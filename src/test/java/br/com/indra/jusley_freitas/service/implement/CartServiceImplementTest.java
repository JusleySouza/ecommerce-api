package br.com.indra.jusley_freitas.service.implement;

import br.com.indra.jusley_freitas.dto.request.cart.CartItemRequestDTO;
import br.com.indra.jusley_freitas.dto.response.cart.CartItemResponseDTO;
import br.com.indra.jusley_freitas.dto.response.cart.CartResponseDTO;
import br.com.indra.jusley_freitas.enums.CartStatus;
import br.com.indra.jusley_freitas.exception.ResourceNotFoundException;
import br.com.indra.jusley_freitas.model.Cart;
import br.com.indra.jusley_freitas.model.CartItem;
import br.com.indra.jusley_freitas.model.Product;
import br.com.indra.jusley_freitas.repository.CartItemRepository;
import br.com.indra.jusley_freitas.repository.CartRepository;
import br.com.indra.jusley_freitas.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CartServiceImplementTest {

    @InjectMocks
    private CartServiceImplement cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ProductRepository productRepository;

    private UUID userId;
    private UUID productId;

    private Cart cart;
    private Product product;
    private CartItem cartItem;

    private CartItemRequestDTO requestDTO;
    private CartResponseDTO responseDTO;

    @BeforeEach
    void setUp() {

        userId = UUID.randomUUID();
        productId = UUID.randomUUID();

        cart = Cart.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .status(CartStatus.ACTIVE)
                .build();

        product = Product.builder()
                .id(productId)
                .name("Product Test")
                .price(BigDecimal.valueOf(10.0))
                .build();

        cartItem = CartItem.builder()
                .id(UUID.randomUUID())
                .cartId(cart.getId())
                .productId(product.getId())
                .quantity(2)
                .priceSnapshot(BigDecimal.valueOf(10.0))
                .build();

        requestDTO = new CartItemRequestDTO(productId, 3);

        CartItemResponseDTO itemResponse =
                new CartItemResponseDTO(productId, 3, new BigDecimal(100), new BigDecimal(100));

        responseDTO = new CartResponseDTO(userId, List.of(itemResponse), BigDecimal.valueOf(30.0));
    }

    @Test
    void shouldReturnCartSuccessfully() {
        when(cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE)).thenReturn(Optional.of(cart));

        when(cartItemRepository.findByCartId(cart.getId())).thenReturn(List.of(cartItem));

        CartResponseDTO response = cartService.getCart(userId);

        assertNotNull(response);
        verify(cartRepository).findByUserIdAndStatus(userId, CartStatus.ACTIVE);
        verify(cartItemRepository).findByCartId(cart.getId());
    }

    @Test
    void shouldAddNewItemToCart() {

        when(cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE)).thenReturn(Optional.of(cart));

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        when(cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)).thenReturn(Optional.empty());

        when(cartItemRepository.findByCartId(cart.getId())).thenReturn(List.of(cartItem));

        CartResponseDTO response = cartService.addItem(userId, requestDTO);

        assertNotNull(response);
        verify(cartItemRepository).save(any(CartItem.class));
    }

    @Test
    void shouldUpdateExistingItemQuantity() {
        when(cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE)).thenReturn(Optional.of(cart));

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        when(cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)).thenReturn(Optional.of(cartItem));

        when(cartItemRepository.findByCartId(cart.getId())).thenReturn(List.of(cartItem));

        cartService.addItem(userId, requestDTO);

        assertEquals(5, cartItem.getQuantity());
        verify(cartItemRepository).save(cartItem);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        when(cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE)).thenReturn(Optional.of(cart));

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cartService.addItem(userId, requestDTO));
    }

    @Test
    void shouldUpdateItemSuccessfully() {
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));

        when(cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)).thenReturn(Optional.of(cartItem));

        cartService.updateItem(userId, productId, 5);

        assertEquals(5, cartItem.getQuantity());
        verify(cartItemRepository).save(cartItem);
    }

    @Test
    void shouldThrowExceptionWhenCartNotFoundOnUpdate() {
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cartService.updateItem(userId, productId, 2));
    }

    @Test
    void shouldThrowExceptionWhenItemNotFoundOnUpdate() {
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));

        when(cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cartService.updateItem(userId, productId, 2));
    }

    @Test
    void shouldRemoveItemSuccessfully() {
        when(cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE)).thenReturn(Optional.of(cart));

        when(cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)).thenReturn(Optional.of(cartItem));

        cartService.removeItem(userId, productId);

        verify(cartItemRepository).delete(cartItem);
    }

    @Test
    void shouldThrowExceptionWhenItemNotFoundOnRemove() {
        when(cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE)).thenReturn(Optional.of(cart));

        when(cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cartService.removeItem(userId, productId));
    }

    @Test
    void shouldClearCartSuccessfully() {
        when(cartRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE)).thenReturn(Optional.of(cart));

        cartService.clearCart(userId);

        verify(cartItemRepository).deleteByCartId(cart.getId());
    }
}
