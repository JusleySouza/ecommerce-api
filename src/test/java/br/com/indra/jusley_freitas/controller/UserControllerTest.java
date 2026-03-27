package br.com.indra.jusley_freitas.controller;

import br.com.indra.jusley_freitas.dto.request.user.UpdateUserDTO;
import br.com.indra.jusley_freitas.dto.request.user.UserRequestDTO;
import br.com.indra.jusley_freitas.dto.response.user.UserResponseDTO;
import br.com.indra.jusley_freitas.exception.ResourceNotFoundException;
import br.com.indra.jusley_freitas.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID userId;
    private UserRequestDTO requestDTO;
    private UpdateUserDTO updateDTO;
    private UserResponseDTO responseDTO;

    @BeforeEach
    void setup() {
        userId = UUID.randomUUID();

        requestDTO = new UserRequestDTO(
                "Laura",
                "714.662.300-96",
                "laura@email.com"
        );

        updateDTO = new UpdateUserDTO(
                "Laura Updated",
                "laura@email.com"
        );

        responseDTO = new UserResponseDTO(
                userId,
                "Laura",
                "laura@email.com"
        );
    }

    @Test
    void shouldCreateUser() throws Exception {
        when(userService.create(any())).thenReturn(responseDTO);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Laura"));

        verify(userService).create(any());
    }

    @Test
    void shouldReturnUserById() throws Exception {
        when(userService.findById(userId)).thenReturn(responseDTO);

        mockMvc.perform(get("/users/{userId}", userId)).andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("laura@email.com"));
    }

    @Test
    void shouldReturnAllUsers() throws Exception {
        when(userService.findAll()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/users")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Laura"));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        doNothing().when(userService).update(eq(userId), any());

        mockMvc.perform(put("/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isNoContent());

        verify(userService).update(eq(userId), any());
    }

    @Test
    void shouldDeleteUser() throws Exception {
        doNothing().when(userService).delete(userId);

        mockMvc.perform(delete("/users/{userId}", userId)).andExpect(status().isNoContent());

        verify(userService).delete(userId);
    }

    @Test
    void shouldReturnBadRequestWhenInvalidInput() throws Exception {
        UserRequestDTO invalid = new UserRequestDTO("", "", "");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
        when(userService.findById(userId)).thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/users/{userId}", userId)).andExpect(status().isNotFound());
    }
}
