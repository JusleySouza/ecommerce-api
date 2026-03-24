package br.com.indra.jusley_freitas.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO for updating a product. Contains the fields that can be updated for a product in the system.")
public record UpdateProductDTO(
        @NotEmpty(message = "{name.not.empty}")
        @Size(min = 2, max = 45, message = "{name.size}")
        @Schema(description = "Product name", type = "String", example = "Geladeira")
        String name,
        @NotEmpty(message = "{description.not.empty}")
        @Size(min = 10, max = 255, message = "{description.size}")
        @Schema(description = "Product description", type = "String", example = "Brastemp")
        String description,
        @NotNull(message = "{stock.not.null}")
        @Min(value=0, message = "{stock.not.less.than}")
        @Schema(description = "Product stock", type = "int", example = "15")
        int stockQuantity
) {}
