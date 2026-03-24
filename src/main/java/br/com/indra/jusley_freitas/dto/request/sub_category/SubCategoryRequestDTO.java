package br.com.indra.jusley_freitas.dto.request.sub_category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO for creating a new sub_category. Contains all necessary fields to create a sub_category in the system.")
public record SubCategoryRequestDTO(
        @NotEmpty(message = "{nameSubCategory.not.empty}")
        @Size(min = 3, max = 45, message = "{nameSubCategory.size}")
        @Schema(description = "SubCategory name", type = "String", example = "Celular")
        String name,
        @NotEmpty(message = "{descriptionSubCategory.not.empty}")
        @Size(min = 10, max = 255, message = "{descriptionSubCategory.size}")
        @Schema(description = "SubCategory name", type = "String", example = "Smartphone Pro")
        String description
) {}
