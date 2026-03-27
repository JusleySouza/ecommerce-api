package br.com.indra.jusley_freitas.service.implement;

import br.com.indra.jusley_freitas.config.LoggerConfig;
import br.com.indra.jusley_freitas.dto.request.user.UpdateUserDTO;
import br.com.indra.jusley_freitas.dto.request.user.UserRequestDTO;
import br.com.indra.jusley_freitas.dto.response.user.UserResponseDTO;
import br.com.indra.jusley_freitas.exception.*;
import br.com.indra.jusley_freitas.mapper.UserMapper;
import br.com.indra.jusley_freitas.model.User;
import br.com.indra.jusley_freitas.repository.UserRepository;
import br.com.indra.jusley_freitas.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {

    private final UserRepository userRepository;

    public UserResponseDTO create(UserRequestDTO requestDTO) {
        if(userRepository.existsByEmail(requestDTO.email())) {
            throw new DuplicateEmailException("Could not register user. There is already a user registered with this email: " + requestDTO.email());
        }

        if(userRepository.existsByCpf(requestDTO.cpf())) {
            throw new DuplicateCpfException("Could not register user. There is already a user registered with this cpf: " + requestDTO.cpf());
        }

        User user = UserMapper.toModel(requestDTO);

        LoggerConfig.LOGGER_USER.info("User " + user.getName() + " salved successfully!");
        return UserMapper.toResponse(userRepository.save(user));
    }

    public UserResponseDTO findById(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("We were unable to find a user with this ID: " + userId));

        LoggerConfig.LOGGER_USER.info("User found successfully!");
        return UserMapper.toResponse(user);
    }

    public List<UserResponseDTO> findAll() {
        LoggerConfig.LOGGER_USER.info("User list successfully executed!");
        return UserMapper.toResponseList(userRepository.findAll());
    }

    public void update(UUID userId, UpdateUserDTO updateUserDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("We were unable to find a user with this ID: " + userId));

        if(userRepository.existsByEmail(updateUserDTO.email())) {
            throw new DuplicateEmailException("Could not register user. There is already a user registered with this email: " + updateUserDTO.email());
        }

        UserMapper.updateUser(user, updateUserDTO);

        LoggerConfig.LOGGER_USER.info("User " + user.getName() + " updated successfully!");
        userRepository.save(user);
    }

    public void delete(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("We were unable to find a user with this ID: " + userId));

        LoggerConfig.LOGGER_USER.info("User " + user.getName() + " deleted successfully!");
        userRepository.delete(user);
    }
}
