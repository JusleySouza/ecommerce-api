package br.com.indra.jusley_freitas.controller;

import br.com.indra.jusley_freitas.dto.request.sub_category.SubCategoryRequestDTO;
import br.com.indra.jusley_freitas.dto.response.sub_category.SubCategoryResponseDTO;
import br.com.indra.jusley_freitas.exception.ResourceNotFoundException;
import br.com.indra.jusley_freitas.service.SubCategoryService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SubCategoryController.class)
class SubCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubCategoryService service;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID categoryId;
    private UUID subCategoryId;
    private SubCategoryRequestDTO requestDTO;
    private SubCategoryResponseDTO responseDTO;

    @BeforeEach
    void setup() {
        categoryId = UUID.randomUUID();
        subCategoryId = UUID.randomUUID();

        requestDTO = new SubCategoryRequestDTO(
                "Celular",
                "Celular Novo"
        );

        responseDTO = mock(SubCategoryResponseDTO.class);
    }

    @Test
    void shouldCreateSubCategory() throws Exception {
        when(service.createSubCategory(any(), any())).thenReturn(responseDTO);

        mockMvc.perform(post("/categories/{categoryId}/sub_categories", categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated());

        verify(service).createSubCategory(any(), any());
    }

    @Test
    void shouldReturn400WhenInvalidBody() throws Exception {
        mockMvc.perform(post("/categories/{categoryId}/sub_categories", categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnSubCategories() throws Exception {
        when(service.findAllSubCategoriesByCategoryId(categoryId)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/categories/{categoryId}/sub_categories", categoryId)).andExpect(status().isOk());
    }

    @Test
    void shouldReturn404WhenNoSubCategories() throws Exception {
        when(service.findAllSubCategoriesByCategoryId(categoryId)).thenThrow(new ResourceNotFoundException("not found"));

        mockMvc.perform(get("/categories/{categoryId}/sub_categories", categoryId)).andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateSubCategory() throws Exception {
        mockMvc.perform(put("/categories/{categoryId}/sub_categories/{subCategoryId}", categoryId, subCategoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isNoContent());

        verify(service).updateSubCategory(any(), any(), any());
    }

    @Test
    void shouldReturn404WhenUpdatingNonExisting() throws Exception {
        doThrow(new ResourceNotFoundException("not found")).when(service).updateSubCategory(any(), any(), any());

        mockMvc.perform(put("/categories/{categoryId}/sub_categories/{subCategoryId}", categoryId, subCategoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteSubCategory() throws Exception {
        mockMvc.perform(delete("/categories/{categoryId}/sub_categories/{subCategoryId}", categoryId, subCategoryId))
                .andExpect(status().isNoContent());

        verify(service).deleteSubCategory(categoryId, subCategoryId);
    }

    @Test
    void shouldReturn404WhenDeletingNonExisting() throws Exception {
        doThrow(new ResourceNotFoundException("not found")).when(service).deleteSubCategory(categoryId, subCategoryId);

        mockMvc.perform(delete("/categories/{categoryId}/sub_categories/{subCategoryId}", categoryId, subCategoryId))
                .andExpect(status().isNotFound());
    }
}
