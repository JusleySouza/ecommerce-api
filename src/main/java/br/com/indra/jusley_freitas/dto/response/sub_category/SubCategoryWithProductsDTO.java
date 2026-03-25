package br.com.indra.jusley_freitas.dto.response.sub_category;

import br.com.indra.jusley_freitas.dto.response.product.ProductResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

@Schema(description = "DTO for sub_category response with products. " +
        "Contains all fields that represent a sub_category in the system, along with a list of products that belong to that sub_category.")
public record SubCategoryWithProductsDTO(
        @Schema(description = "SubCategory identifier", type = "UUID", example = "7494ef92-cbc1-4ed1-bf9b-f9873f916424")
        UUID id,
        @Schema(description = "SubCategory name", type = "String", example = "Celular")
        String name,
        @Schema(description = "List of products", type = "List<ProductResponseDTO>")
        List<ProductResponseDTO> products
) {}
