package br.com.indra.jusley_freitas.dto.request.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO for creating a new category. Contains all necessary fields to create a category in the system.")
public record CategoryRequestDTO(
        @NotEmpty(message = "{nameCategory.not.empty}")
        @Size(min = 3, max = 50, message = "{nameCategory.size}")
        @Schema(description = "Category name", type = "String", example = "Eletronicos")
        String name
) {}
