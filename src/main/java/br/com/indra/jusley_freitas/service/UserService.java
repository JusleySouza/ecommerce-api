package br.com.indra.jusley_freitas.service;

import br.com.indra.jusley_freitas.dto.request.user.UpdateUserDTO;
import br.com.indra.jusley_freitas.dto.request.user.UserRequestDTO;
import br.com.indra.jusley_freitas.dto.response.user.UserResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {

    public UserResponseDTO create(UserRequestDTO requestDTO);

    public UserResponseDTO findById(UUID userId);

    public List<UserResponseDTO> findAll();

    public void update(UUID userId, UpdateUserDTO updateUserDTO);

    public void delete(UUID userId);

}
