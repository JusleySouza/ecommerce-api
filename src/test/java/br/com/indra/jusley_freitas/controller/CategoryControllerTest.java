package br.com.indra.jusley_freitas.controller;

import br.com.indra.jusley_freitas.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.indra.jusley_freitas.dto.request.category.CategoryRequestDTO;
import br.com.indra.jusley_freitas.dto.response.category.CategoryResponseDTO;
import br.com.indra.jusley_freitas.dto.response.category.CategoryWithProductsResponseDTO;
import br.com.indra.jusley_freitas.service.CategoryService;

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

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService service;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID categoryId;
    private CategoryResponseDTO responseDTO;
    private CategoryRequestDTO requestDTO;

    @BeforeEach
    void setup() {
        categoryId = UUID.randomUUID();

        requestDTO = new CategoryRequestDTO("Eletronicos");

        responseDTO = new CategoryResponseDTO(
                categoryId,
                "Eletronicos",
                null,
                null
        );
    }

    @Test
    void shouldCreateCategory() throws Exception {
        when(service.createCategory(any())).thenReturn(responseDTO);

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Eletronicos"));

        verify(service).createCategory(any());
    }

    @Test
    void shouldReturnAllCategories() throws Exception {
        when(service.findAllCategories()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Eletronicos"));
    }

    @Test
    void shouldUpdateCategory() throws Exception {
        doNothing().when(service).updateCategory(any(), eq(categoryId));

        mockMvc.perform(put("/categories/{categoryId}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isNoContent());

        verify(service).updateCategory(any(), eq(categoryId));
    }

    @Test
    void shouldDeleteCategory() throws Exception {
        doNothing().when(service).deleteCategory(categoryId);

        mockMvc.perform(delete("/categories/{categoryId}", categoryId)).andExpect(status().isNoContent());

        verify(service).deleteCategory(categoryId);
    }

    @Test
    void shouldReturnCategoryById() throws Exception {
        when(service.findByCategoryId(categoryId)).thenReturn(responseDTO);

        mockMvc.perform(get("/categories/{categoryId}", categoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Eletronicos"));
    }

    @Test
    void shouldReturnProductsByCategory() throws Exception {

        CategoryWithProductsResponseDTO response = new CategoryWithProductsResponseDTO(categoryId, "Eletronicos", List.of());

        when(service.findAllProductsByCategory(categoryId)).thenReturn(response);

        mockMvc.perform(get("/categories/{categoryId}/products", categoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Eletronicos"));
    }

    @Test
    void shouldReturnBadRequestWhenInvalidInput() throws Exception {
        CategoryRequestDTO invalidRequest = new CategoryRequestDTO("");

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnNotFoundWhenCategoryDoesNotExist() throws Exception {
        when(service.findByCategoryId(categoryId)).thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/categories/{categoryId}", categoryId)).andExpect(status().isNotFound());
    }

}
