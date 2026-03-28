package br.com.indra.jusley_freitas.controller;

import br.com.indra.jusley_freitas.dto.request.cart.CartItemRequestDTO;
import br.com.indra.jusley_freitas.dto.request.cart.UpdateCartItemRequestDTO;
import br.com.indra.jusley_freitas.dto.response.cart.CartResponseDTO;
import br.com.indra.jusley_freitas.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users/{userId}/cart")
@RequiredArgsConstructor
@Tag(name = "Cart", description = "Endpoints for cart management")
public class CartController {

    private final CartService cartService;

    @GetMapping
    @Operation(summary = "Search cart by User ID", description = "Returns the cart associated with the specified User ID.")
    public ResponseEntity<CartResponseDTO> getCart(@PathVariable UUID userId) {
        return new ResponseEntity<>(cartService.getCart(userId), HttpStatus.OK);
    }

    @PostMapping("/items")
    @Operation(summary = "Add item to cart", description = "Add an item to the cart for the specified User ID.")
    public ResponseEntity<CartResponseDTO> addItem(@PathVariable UUID userId, @Valid @RequestBody CartItemRequestDTO request) {
        return new ResponseEntity<>(cartService.addItem(userId, request), HttpStatus.CREATED);
    }

    @PutMapping("/items/{productId}")
    @Operation(summary = "Update item to cart", description = "Update the quantity of an item in the cart for the specified User ID.")
    public ResponseEntity<Void> updateItem(@PathVariable UUID userId, @PathVariable UUID productId, @Valid @RequestBody UpdateCartItemRequestDTO request) {
        cartService.updateItem(userId, productId, request.quantity());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
