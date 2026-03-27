package br.com.indra.jusley_freitas.service.implement;

import br.com.indra.jusley_freitas.dto.request.inventory.InventoryTransactionRequestDTO;
import br.com.indra.jusley_freitas.dto.response.inventory.CompleteInventoryTransactionResponseDTO;
import br.com.indra.jusley_freitas.dto.response.inventory.InventoryTransactionResponseDTO;
import br.com.indra.jusley_freitas.enums.InventoryReason;
import br.com.indra.jusley_freitas.exception.InsufficientStockException;
import br.com.indra.jusley_freitas.exception.ResourceNotFoundException;
import br.com.indra.jusley_freitas.model.InventoryTransaction;
import br.com.indra.jusley_freitas.model.Product;
import br.com.indra.jusley_freitas.repository.InventoryTransactionRepository;
import br.com.indra.jusley_freitas.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryTransactionServiceImplementTest {

    @Mock
    private InventoryTransactionRepository repository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private InventoryTransactionServiceImplement service;

    private UUID productId;
    private Product product;
    private InventoryTransactionRequestDTO requestDTO;
    private InventoryTransaction inventoryTransaction;

    @BeforeEach
    void setup() {
        productId = UUID.randomUUID();

        product = new Product();
        product.setId(productId);
        product.setStockQuantity(50);

        requestDTO = new InventoryTransactionRequestDTO(10, "PURCHASE");

        inventoryTransaction = InventoryTransaction.builder()
                .id(UUID.randomUUID())
                .productId(productId)
                .delta(10)
                .reason(InventoryReason.PURCHASE)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldAddStockSuccessfully() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        InventoryTransactionResponseDTO response = service.addStock(productId, requestDTO);

        assertNotNull(response);
        assertEquals(60, response.currentStock());

        verify(productRepository).save(product);
        verify(repository).save(any(InventoryTransaction.class));
    }

    @Test
    void shouldThrowWhenProductNotFoundOnAddStock() {
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.addStock(productId, requestDTO));
    }

    @Test
    void shouldRemoveStockSuccessfully() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        InventoryTransactionResponseDTO response = service.removeStock(productId, requestDTO);

        assertNotNull(response);
        assertEquals(40, response.currentStock());

        verify(productRepository).save(product);
        verify(repository).save(any(InventoryTransaction.class));
    }

    @Test
    void shouldThrowWhenProductNotFoundOnRemoveStock() {
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.removeStock(productId, requestDTO));
    }

    @Test
    void shouldThrowWhenInsufficientStock() {
        product.setStockQuantity(5);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        assertThrows(InsufficientStockException.class, () -> service.removeStock(productId, requestDTO));

        verify(repository, never()).save(any());
    }

    @Test
    void shouldReturnAllTransactions() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(repository.findByProductId(productId)).thenReturn(List.of(inventoryTransaction));

        List<CompleteInventoryTransactionResponseDTO> response = service.getAllTransactions(productId);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(productId, response.get(0).productId());
    }

    @Test
    void shouldThrowWhenNoTransactionsFound() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(repository.findByProductId(productId)).thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class, () -> service.getAllTransactions(productId));
    }

    @Test
    void shouldThrowWhenProductNotFoundOnGetTransactions() {
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getAllTransactions(productId));
    }
}
