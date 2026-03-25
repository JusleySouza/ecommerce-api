package br.com.indra.jusley_freitas.mapper;

import br.com.indra.jusley_freitas.dto.response.category.CategoryWithProductsResponseDTO;
import br.com.indra.jusley_freitas.dto.response.product.ProductResponseDTO;
import br.com.indra.jusley_freitas.dto.response.sub_category.SubCategoryWithProductsDTO;
import br.com.indra.jusley_freitas.model.Category;
import br.com.indra.jusley_freitas.model.Product;
import br.com.indra.jusley_freitas.model.SubCategory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CategoryAggregationMapper {

    public static CategoryWithProductsResponseDTO mapToGroupedResponse(Category category, List<Product> products) {

        Map<SubCategory, List<Product>> grouped = products.stream().collect(Collectors.groupingBy(Product::getSubCategory));

        List<SubCategoryWithProductsDTO> subCategories = grouped.entrySet()
                .stream()
                .map(entry ->
                    {SubCategory sub = entry.getKey();

                    List<ProductResponseDTO> productDTOs = entry.getValue()
                            .stream()
                            .map(ProductMapper::toResponse)
                            .toList();

                    return new SubCategoryWithProductsDTO(
                            sub.getId(),
                            sub.getName(),
                            productDTOs
                    );
                })
                .toList();

        return new CategoryWithProductsResponseDTO(
                category.getId(),
                category.getName(),
                subCategories
        );
    }
}
