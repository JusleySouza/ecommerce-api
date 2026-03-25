package br.com.indra.jusley_freitas.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "DTO for sub_category response. Contains all fields that represent a sub_category in the system.")
public record SubCategoryResponseDTO(
        @Schema(description = "SubCategory identifier", type = "UUID", example = "7494ef92-cbc1-4ed1-bf9b-f9873f916424")
        UUID id,
        @Schema(description = "SubCategory name", type = "String", example = "Celular")
        String name,
        @Schema(description = "SubCategory description", type = "String", example = "Smartphone Pro")
        String description,
        @Schema(description = "create date", type = "LocalDateTime", example = "2026-03-23T17:43:24.026Z")
        LocalDateTime createdAt,
        @Schema(description = "update date", type = "LocalDateTime", example = "2026-03-23T17:43:24.026Z")
        LocalDateTime updatedAt,
        @Schema(description = "Category identifier", type = "UUID", example = "7494ef92-cbc1-4ed1-bf9b-f9873f916424")
        UUID idCategory
) {}
