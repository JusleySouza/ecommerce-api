package br.com.indra.jusley_freitas.dto.response.category;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "DTO for category response. Contains all fields that represent a category in the system.")
public record CategoryResponseDTO(
        @Schema(description = "Category identifier", type = "UUID", example = "7494ef92-cbc1-4ed1-bf9b-f9873f916424")
        UUID id,
        @Schema(description = "Category name", type = "String", example = "Eletronicos")
        String name,
        @Schema(description = "create date", type = "LocalDateTime", example = "2026-03-23T17:43:24.026Z")
        LocalDateTime createdAt,
        @Schema(description = "update date", type = "LocalDateTime", example = "2026-03-23T17:43:24.026Z")
        LocalDateTime updatedAt
) {}
