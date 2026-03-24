package br.com.indra.jusley_freitas.mapper;

import br.com.indra.jusley_freitas.dto.request.ProductRequestDTO;
import br.com.indra.jusley_freitas.dto.request.UpdatePriceProductDTO;
import br.com.indra.jusley_freitas.dto.request.UpdateProductDTO;
import br.com.indra.jusley_freitas.dto.response.ProductResponseDTO;
import br.com.indra.jusley_freitas.model.Product;

import java.time.LocalDateTime;

public class ProductMapper {

    public static Product toEntity(ProductRequestDTO dto) {
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

    public static Product updateEntity(Product entity, UpdateProductDTO dto) {
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setStockQuantity(dto.stockQuantity());
        entity.setUpdatedAt(LocalDateTime.now());
        return entity;
    }

    public static Product updatePriceProduct(Product entity, UpdatePriceProductDTO dto) {
        entity.setPrice(dto.price());
        entity.setUpdatedAt(LocalDateTime.now());
        return entity;
    }

    public static Product deleteProduct(Product product) {
        product.setActive(Boolean.FALSE);
        product.setUpdatedAt(LocalDateTime.now());
        return product;
    }

    public static Product reactivateProduct(Product product) {
        product.setActive(Boolean.TRUE);
        product.setUpdatedAt(LocalDateTime.now());
        return product;
    }

}
