package br.com.indra.jusley_freitas.mapper;

import br.com.indra.jusley_freitas.dto.request.product.ProductRequestDTO;
import br.com.indra.jusley_freitas.dto.request.product.UpdatePriceProductDTO;
import br.com.indra.jusley_freitas.dto.request.product.UpdateProductDTO;
import br.com.indra.jusley_freitas.dto.response.ProductResponseDTO;
import br.com.indra.jusley_freitas.model.Product;
import br.com.indra.jusley_freitas.model.SubCategory;

import java.time.LocalDateTime;
import java.util.List;

public class ProductMapper {

    public static Product toEntity(ProductRequestDTO dto, SubCategory subCategory) {
        return Product.builder()
                .name(dto.name())
                .description(dto.description())
                .sku(dto.sku())
                .price(dto.price())
                .costPrice(dto.costPrice())
                .stockQuantity(dto.stockQuantity())
                .active(true)
                .subCategory(subCategory)
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
                entity.getSubCategory().getId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static List<ProductResponseDTO> toResponseList(List<Product> products) {
        return products.stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    public static Product updateEntity(Product entity, UpdateProductDTO dto, SubCategory subCategory) {
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setStockQuantity(dto.stockQuantity());
        entity.setSubCategory(subCategory);
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
