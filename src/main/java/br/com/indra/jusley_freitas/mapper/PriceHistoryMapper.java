package br.com.indra.jusley_freitas.mapper;

import br.com.indra.jusley_freitas.dto.request.UpdatePriceProductDTO;
import br.com.indra.jusley_freitas.model.PriceHistory;
import br.com.indra.jusley_freitas.model.Product;

import java.time.LocalDateTime;

public class PriceHistoryMapper {

    public static PriceHistory toEntity(Product product, UpdatePriceProductDTO newPrice) {
        return PriceHistory.builder()
                .product(product)
                .oldPrice(product.getPrice())
                .newPrice(newPrice.price())
                .dateOfChange(LocalDateTime.now())
                .build();
    }
}
