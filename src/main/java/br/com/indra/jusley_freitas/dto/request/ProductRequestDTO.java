package br.com.indra.jusley_freitas.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "DTO for creating a new product. Contains all necessary fields to create a product in the system.")
public record ProductRequestDTO(
        @NotEmpty(message = "{name.not.empty}")
        @Size(min = 2, max = 45, message = "{name.size}")
        @Schema(description = "Product name", type = "String", example = "Geladeira")
        String name,
        @NotEmpty(message = "{description.not.empty}")
        @Size(min = 10, max = 255, message = "{description.size}")
        @Schema(description = "Product description", type = "String", example = "Brastemp")
        String description,
        @NotNull(message = "{sku.not.null}")
        @Schema(description = "Product sku", type = "String", example = "123456")
        String sku,
        @NotNull(message = "{price.not.null}")
        @Min(value=0, message = "{price.not.less.than}")
        @Schema(description = "Product price", type = "BigDecimal", example = "4.500")
        BigDecimal price,
        @NotNull(message = "{cost.price.not.null}")
        @Min(value=0, message = "{cost.price.not.less.than}")
        @Schema(description = "Product cost price", type = "BigDecimal", example = "3.300")
        BigDecimal costPrice,
        @NotNull(message = "{stock.not.null}")
        @Min(value=0, message = "{stock.not.less.than}")
        @Schema(description = "Product stock", type = "int", example = "15")
        int stockQuantity
) {}
