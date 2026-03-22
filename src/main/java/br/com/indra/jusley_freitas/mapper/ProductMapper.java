package br.com.indra.jusley_freitas.mapper;

import br.com.indra.jusley_freitas.dto.request.ProductRequestDTO;
import br.com.indra.jusley_freitas.dto.response.ProductResponseDTO;
import br.com.indra.jusley_freitas.model.Product;

public class ProductMapper {

    public static Product toModel(ProductRequestDTO dto) {
        return Product.builder()
                .name(dto.name())
                .description(dto.description())
                .sku(dto.sku())
                .price(dto.price())
                .costPrice(dto.costPrice())
                .stockQuantity(dto.stockQuantity())
                .active(true)
                .build();
    }

    public static ProductResponseDTO toResponse(Product entity) {
        return new ProductResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getSku(),
                entity.getPrice(),
                entity.getCostPrice(),
                entity.getStockQuantity(),
                entity.getActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

}
