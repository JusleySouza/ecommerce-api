package br.com.indra.jusley_freitas.dto.response.user;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "DTO for user response. Contains the fields that represent a user in the system.")
public record UserResponseDTO(
        @Schema(description = "User identifier", type = "UUID", example = "7494ef92-cbc1-4ed1-bf9b-f9873f916424")
        UUID id,
        @Schema(description = "User name", type = "String", example = "Ana Silva")
        String name,
        @Schema(description = "User email", type = "String", example = "ana.silva@gmail.com")
        String email
) {}
