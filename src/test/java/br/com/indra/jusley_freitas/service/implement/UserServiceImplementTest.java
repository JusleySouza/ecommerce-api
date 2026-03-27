package br.com.indra.jusley_freitas.service.implement;

import br.com.indra.jusley_freitas.dto.request.user.UpdateUserDTO;
import br.com.indra.jusley_freitas.dto.request.user.UserRequestDTO;
import br.com.indra.jusley_freitas.dto.response.user.UserResponseDTO;
import br.com.indra.jusley_freitas.exception.DuplicateCpfException;
import br.com.indra.jusley_freitas.exception.DuplicateEmailException;
import br.com.indra.jusley_freitas.exception.ResourceNotFoundException;
import br.com.indra.jusley_freitas.model.User;
import br.com.indra.jusley_freitas.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplementTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImplement service;

    private UUID userId;
    private User user;
    private UserRequestDTO requestDTO;
    private UpdateUserDTO updateDTO;

    @BeforeEach
    void setup() {
        userId = UUID.randomUUID();

        user = User.builder()
                .id(userId)
                .name("Laura")
                .email("laura@email.com")
                .cpf("714.662.300-96")
                .build();

        requestDTO = new UserRequestDTO("Laura", "714.662.300-96", "laura@email.com");

        updateDTO = new UpdateUserDTO("Laura Updated ", "laura@email.com");
    }

    @Test
    void shouldCreateUserSuccessfully() {
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.existsByCpf(any())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        UserResponseDTO response = service.create(requestDTO);

        assertNotNull(response);
        assertEquals("Laura", response.name());

        verify(userRepository).save(any());
    }

    @Test
    void shouldThrowWhenEmailAlreadyExists() {
        when(userRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(DuplicateEmailException.class, () -> service.create(requestDTO));

        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenCpfAlreadyExists() {
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.existsByCpf(any())).thenReturn(true);

        assertThrows(DuplicateCpfException.class, () -> service.create(requestDTO));

        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldReturnUserById() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserResponseDTO response = service.findById(userId);

        assertNotNull(response);
        assertEquals("Laura", response.name());
    }

    @Test
    void shouldThrowWhenUserNotFoundById() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(userId));
    }

    @Test
    void shouldReturnAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserResponseDTO> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("Laura", result.get(0).name());
    }

    @Test
    void shouldReturnEmptyListWhenNoUsers() {
        when(userRepository.findAll()).thenReturn(List.of());

        List<UserResponseDTO> result = service.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail(updateDTO.email())).thenReturn(false);

        service.update(userId, updateDTO);

        verify(userRepository).save(any());
    }

    @Test
    void shouldThrowWhenUpdatingNonExistingUser() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.update(userId, updateDTO));
    }

    @Test
    void shouldThrowWhenUpdatingWithDuplicateEmail() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail(updateDTO.email())).thenReturn(true);

        assertThrows(DuplicateEmailException.class, () -> service.update(userId, updateDTO));
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        service.delete(userId);

        verify(userRepository).delete(user);
    }

    @Test
    void shouldThrowWhenDeletingNonExistingUser() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.delete(userId));
    }
}
