package br.com.indra.jusley_freitas.mapper;

import br.com.indra.jusley_freitas.dto.request.product.UpdatePriceProductDTO;
import br.com.indra.jusley_freitas.dto.response.history.HistoryProductResponseDTO;
import br.com.indra.jusley_freitas.model.PriceHistory;
import br.com.indra.jusley_freitas.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PriceHistoryMapperTest {

    private PriceHistory history;

    @BeforeEach
    void setup() {
        Product product = new Product();
        product.setName("Geladeira");

        history = PriceHistory.builder()
                .id(UUID.randomUUID())
                .product(product)
                .oldPrice(new BigDecimal("2500"))
                .newPrice(new BigDecimal("3000"))
                .dateOfChange(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldMapToEntityCorrectly() {
        Product product = new Product();
        product.setName("Geladeira");
        product.setPrice(new BigDecimal("2800"));

        UpdatePriceProductDTO dto = new UpdatePriceProductDTO(new BigDecimal("3000"));

        PriceHistory entity = PriceHistoryMapper.toEntity(product, dto);

        assertEquals(new BigDecimal("2800"), entity.getOldPrice());
        assertEquals(new BigDecimal("3000"), entity.getNewPrice());
        assertEquals(product, entity.getProduct());
        assertNotNull(entity.getDateOfChange());
    }

    @Test
    void shouldMapToResponseCorrectly() {
        Product product = new Product();
        product.setName("Geladeira");

        PriceHistory entity = PriceHistory.builder()
                .id(UUID.randomUUID())
                .product(product)
                .oldPrice(new BigDecimal("2500"))
                .newPrice(new BigDecimal("3000"))
                .dateOfChange(LocalDateTime.now())
                .build();

        HistoryProductResponseDTO response = PriceHistoryMapper.toResponse(entity);

        assertEquals("Geladeira", response.product());
        assertEquals(new BigDecimal("2500"), response.oldPrice());
    }

    @Test
    void shouldMapListCorrectly() {
        List<PriceHistory> list = List.of(history);

        List<HistoryProductResponseDTO> response = PriceHistoryMapper.toResponseList(list);

        assertEquals(1, response.size());
    }

    @Test
    void shouldReturnEmptyListWhenInputIsEmpty() {
        List<HistoryProductResponseDTO> response = PriceHistoryMapper.toResponseList(Collections.emptyList());

        assertTrue(response.isEmpty());
    }

    @Test
    void shouldReturnEmptyListWhenInputIsNull() {
        List<HistoryProductResponseDTO> response = PriceHistoryMapper.toResponseList(null);

        assertTrue(response.isEmpty());
    }

}
