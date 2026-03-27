package br.com.indra.jusley_freitas.dto.request.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO for updating the user.")
public record UpdateUserDTO(
        @NotEmpty(message = "{fullName.not.empty}")
        @Size(min = 2, max = 100, message = "{fullName.size}")
        @Schema(description = "User name", type = "String", example = "Ana Silva")
        String name,
        @Email(message = "{email.not.valid}")
        @NotEmpty(message = "{email.not.empty}")
        @Schema(description = "User email", type = "String", example = "ana.silva@gmail.com")
        String email
) {}
