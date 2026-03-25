package br.com.indra.jusley_freitas.dto.response.category;

import br.com.indra.jusley_freitas.dto.response.sub_category.SubCategoryWithProductsDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

@Schema(description = "DTO for category with products response. " +
        "Contains all fields that represent a category with its subcategories and products in the system.")
public record CategoryWithProductsResponseDTO(
        @Schema(description = "Category identifier", type = "UUID", example = "7494ef92-cbc1-4ed1-bf9b-f9873f916424")
        UUID id,
        @Schema(description = "Category name", type = "String", example = "Eletronicos")
        String name,
        @Schema(description = "List of subcategories with products", type = "List<SubCategoryWithProductsDTO>")
        List<SubCategoryWithProductsDTO> subCategories
) {}
