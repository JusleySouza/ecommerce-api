package br.com.indra.jusley_freitas.service;

import br.com.indra.jusley_freitas.dto.request.cart.CartItemRequestDTO;
import br.com.indra.jusley_freitas.dto.response.cart.CartResponseDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface CartService {

    public CartResponseDTO getCart(UUID userId);

    public CartResponseDTO addItem(UUID userId, CartItemRequestDTO request);

    public void updateItem(UUID userId, UUID itemId, int quantity);

}
