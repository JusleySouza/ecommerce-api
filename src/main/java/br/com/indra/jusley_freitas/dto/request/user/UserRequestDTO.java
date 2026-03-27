package br.com.indra.jusley_freitas.dto.request.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

@Schema(description = "DTO for creating or updating a user. Contains the user's name, CPF, and email.")
public record UserRequestDTO(
        @NotEmpty(message = "{fullName.not.empty}")
        @Size(min = 2, max = 100, message = "{fullName.size}")
        @Schema(description = "User name", type = "String", example = "Ana Silva")
        String name,
        @NotEmpty(message = "{cpf.not.empty}")
        @CPF(message = "{cpf.not.valid}")
        @Schema(description = "User CPF", type = "String", example = "123.456.789-00")
        String cpf,
        @Email(message = "{email.not.valid}")
        @NotEmpty(message = "{email.not.empty}")
        @Schema(description = "User email", type = "String", example = "ana.silva@gmail.com")
        String email
) {}
