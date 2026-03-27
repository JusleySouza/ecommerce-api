package br.com.indra.jusley_freitas.mapper;

import br.com.indra.jusley_freitas.dto.request.user.UpdateUserDTO;
import br.com.indra.jusley_freitas.dto.request.user.UserRequestDTO;
import br.com.indra.jusley_freitas.dto.response.user.UserResponseDTO;
import br.com.indra.jusley_freitas.model.User;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class UserMapper {

    public static User toModel(UserRequestDTO dto) {
        return User.builder()
                .name(dto.name())
                .email(dto.email())
                .cpf(dto.cpf())
                .build();
    }

    public static UserResponseDTO toResponse(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public static List<UserResponseDTO> toResponseList(List<User> user) {
        if (user == null || user.isEmpty()) {
            return Collections.emptyList();
        }
        return user.stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    public static User updateUser(User user, UpdateUserDTO updateUserDTO) {
        user.setName(updateUserDTO.name());
        user.setEmail(updateUserDTO.email());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

}
