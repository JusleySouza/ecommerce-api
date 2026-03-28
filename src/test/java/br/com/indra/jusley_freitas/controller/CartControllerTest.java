package br.com.indra.jusley_freitas.controller;

import br.com.indra.jusley_freitas.dto.request.cart.CartItemRequestDTO;
import br.com.indra.jusley_freitas.dto.request.cart.UpdateCartItemRequestDTO;
import br.com.indra.jusley_freitas.dto.response.cart.CartItemResponseDTO;
import br.com.indra.jusley_freitas.dto.response.cart.CartResponseDTO;
import br.com.indra.jusley_freitas.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CartService cartService;

    private UUID userId;
    private UUID productId;

    private CartItemRequestDTO requestDTO;
    private UpdateCartItemRequestDTO updateRequestDTO;

    private CartResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        productId = UUID.randomUUID();

        requestDTO = new CartItemRequestDTO(productId, 3);
        updateRequestDTO = new UpdateCartItemRequestDTO(5);

        CartItemResponseDTO itemResponse = new CartItemResponseDTO(productId, 3, new BigDecimal("100"), new BigDecimal("100"));

        responseDTO = new CartResponseDTO(userId, List.of(itemResponse), new BigDecimal(100));
    }

    @Test
    void shouldAddItem() throws Exception {
        when(cartService.addItem(eq(userId), any())).thenReturn(responseDTO);

        mockMvc.perform(post("/users/{userId}/cart/items", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.items[0].quantity").value(3))
                .andExpect(jsonPath("$.items[0].productId").value(productId.toString()));
    }

    @Test
    void shouldReturnCart() throws Exception {
        when(cartService.getCart(userId)).thenReturn(responseDTO);

        mockMvc.perform(get("/users/{userId}/cart", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].quantity").value(3));
    }

    @Test
    void shouldUpdateItem() throws Exception {
        mockMvc.perform(put("/users/{userId}/cart/items/{productId}", userId, productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequestDTO)))
                .andExpect(status().isNoContent());

        verify(cartService).updateItem(userId, productId, 5);
    }

    @Test
    void shouldRemoveItem() throws Exception {
        mockMvc.perform(delete("/users/{userId}/cart/items/{productId}", userId, productId))
                .andExpect(status().isNoContent());

        verify(cartService).removeItem(userId, productId);
    }

    @Test
    void shouldClearCart() throws Exception {
        mockMvc.perform(delete("/users/{userId}/cart/items", userId)).andExpect(status().isNoContent());

        verify(cartService).clearCart(userId);
    }
}
