package br.com.indra.jusley_freitas.service.implement;


import br.com.indra.jusley_freitas.dto.response.history.HistoryProductResponseDTO;
import br.com.indra.jusley_freitas.exception.ResourceNotFoundException;
import br.com.indra.jusley_freitas.model.PriceHistory;
import br.com.indra.jusley_freitas.model.Product;
import br.com.indra.jusley_freitas.repository.PriceHistoryRepository;
import br.com.indra.jusley_freitas.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceHistoryServiceImplementTest {

    @InjectMocks
    private PriceHistoryServiceImplement service;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PriceHistoryRepository historyRepository;

    private UUID productId;
    private Product product;
    private PriceHistory history;

    @BeforeEach
    void setup() {
        productId = UUID.randomUUID();

        product = new Product();
        product.setId(productId);
        product.setName("Geladeira");
        product.setPrice(new BigDecimal("2800"));

        history = PriceHistory.builder()
                .id(UUID.randomUUID())
                .product(product)
                .oldPrice(new BigDecimal("2500"))
                .newPrice(new BigDecimal("2800"))
                .dateOfChange(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldReturnPriceHistorySuccessfully() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        when(historyRepository.findByProductId(productId)).thenReturn(List.of(history));

        List<HistoryProductResponseDTO> response = service.getHistoryByProductId(productId);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("Geladeira", response.get(0).product());

        verify(productRepository).findById(productId);
        verify(historyRepository).findByProductId(productId);
    }

    @Test
    void shouldThrowWhenProductNotFound() {
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> service.getHistoryByProductId(productId));

        assertTrue(ex.getMessage().contains(productId.toString()));

        verify(productRepository).findById(productId);
        verify(historyRepository, never()).findByProductId(any());
    }

    @Test
    void shouldThrowWhenNoHistoryFound() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        when(historyRepository.findByProductId(productId)).thenReturn(Collections.emptyList());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> service.getHistoryByProductId(productId));

        assertTrue(ex.getMessage().contains("No price history"));

        verify(productRepository).findById(productId);
        verify(historyRepository).findByProductId(productId);
    }

}
