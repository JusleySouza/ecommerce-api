package br.com.indra.jusley_freitas.mapper;

import br.com.indra.jusley_freitas.dto.request.product.UpdatePriceProductDTO;
import br.com.indra.jusley_freitas.dto.response.HistoryProductResponseDTO;
import br.com.indra.jusley_freitas.model.PriceHistory;
import br.com.indra.jusley_freitas.model.Product;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class PriceHistoryMapper {

    public static PriceHistory toEntity(Product product, UpdatePriceProductDTO newPrice) {
        return PriceHistory.builder()
                .product(product)
                .oldPrice(product.getPrice())
                .newPrice(newPrice.price())
                .dateOfChange(LocalDateTime.now())
                .build();
    }

    public static HistoryProductResponseDTO toResponse(PriceHistory entity) {
        return new HistoryProductResponseDTO(
                entity.getId(),
                entity.getProduct().getName(),
                entity.getOldPrice(),
                entity.getNewPrice(),
                entity.getDateOfChange()
        );
    }

    public static List<HistoryProductResponseDTO> toResponseList(List<PriceHistory> entities) {
        if (entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return entities.stream()
                .map(PriceHistoryMapper::toResponse)
                .toList();
    }
}
