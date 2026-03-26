package br.com.indra.jusley_freitas.controller;

import br.com.indra.jusley_freitas.dto.request.product.ProductRequestDTO;
import br.com.indra.jusley_freitas.dto.request.product.UpdatePriceProductDTO;
import br.com.indra.jusley_freitas.dto.request.product.UpdateProductDTO;
import br.com.indra.jusley_freitas.dto.response.product.ProductResponseDTO;
import br.com.indra.jusley_freitas.exception.ResourceNotFoundException;
import br.com.indra.jusley_freitas.service.PriceHistoryService;
import br.com.indra.jusley_freitas.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @MockBean
    private PriceHistoryService historyService;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID productId;
    private UUID subCategoryId;

    private ProductRequestDTO requestDTO;
    private ProductResponseDTO responseDTO;
    private UpdateProductDTO updateDTO;
    private UpdatePriceProductDTO updatePriceDTO;

    @BeforeEach
    void setup() {
        productId = UUID.randomUUID();
        subCategoryId = UUID.randomUUID();

        requestDTO = new ProductRequestDTO(
                "Samsung",
                "Samsung Pro Max",
                "SKU123",
                new BigDecimal("100"),
                new BigDecimal("80"),
                10,
                subCategoryId
        );

        responseDTO = mock(ProductResponseDTO.class);

        updateDTO = new UpdateProductDTO(
                "Samsung",
                "Samsung Pro Max",
                20,
                subCategoryId
        );

        updatePriceDTO = new UpdatePriceProductDTO(
                new BigDecimal("200")
        );
    }

    @Test
    void shouldCreateProduct() throws Exception {
        when(service.createProduct(any())).thenReturn(responseDTO);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated());

        verify(service).createProduct(any());
    }

    @Test
    void shouldReturnAllProducts() throws Exception {
        when(service.findAllProducts()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/products")).andExpect(status().isOk());
    }

    @Test
    void shouldReturnEmptyList() throws Exception {
        when(service.findAllProducts()).thenReturn(List.of());

        mockMvc.perform(get("/products")).andExpect(status().isOk());
    }

    @Test
    void shouldReturnProductById() throws Exception {
        when(service.findProductById(productId)).thenReturn(responseDTO);

        mockMvc.perform(get("/products/{productId}", productId)).andExpect(status().isOk());

        verify(service).findProductById(productId);
    }

    @Test
    void shouldReturn404WhenProductNotFound() throws Exception {
        when(service.findProductById(productId)).thenThrow(new ResourceNotFoundException("not found"));

        mockMvc.perform(get("/products/{productId}", productId)).andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        mockMvc.perform(put("/products/update/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isNoContent());

        verify(service).updateProduct(any(), eq(productId));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        mockMvc.perform(delete("/products/delete/{productId}", productId)).andExpect(status().isNoContent());

        verify(service).deleteProduct(productId);
    }

    @Test
    void shouldReturnPriceHistory() throws Exception {
        when(historyService.getHistoryByProductId(productId)).thenReturn(List.of());

        mockMvc.perform(get("/products/{productId}/price-history", productId)).andExpect(status().isOk());
    }

    @Test
    void shouldReturnProductsBySubCategory() throws Exception {
        when(service.findAllProductsBySubCategoryId(subCategoryId)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/products/subcategory/{subCategoryId}", subCategoryId)).andExpect(status().isOk());

        verify(service).findAllProductsBySubCategoryId(subCategoryId);
    }

    @Test
    void shouldReturn404WhenSubCategoryHasNoProducts() throws Exception {
        when(service.findAllProductsBySubCategoryId(subCategoryId)).thenThrow(new ResourceNotFoundException("not found"));

        mockMvc.perform(get("/products/subcategory/{subCategoryId}", subCategoryId)).andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateProductPrice() throws Exception {
        mockMvc.perform(patch("/products/update/price/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePriceDTO)))
                .andExpect(status().isNoContent());

        verify(service).updatePriceProduct(any(), eq(productId));
    }

    @Test
    void shouldReturn400WhenInvalidPrice() throws Exception {
        mockMvc.perform(patch("/products/update/price/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReactivateProduct() throws Exception {
        mockMvc.perform(patch("/products/reactivate/{productId}", productId)).andExpect(status().isNoContent());

        verify(service).reactivateProduct(productId);
    }

    @Test
    void shouldReturn404WhenReactivatingNonExistingProduct() throws Exception {
        doThrow(new ResourceNotFoundException("not found")).when(service).reactivateProduct(productId);

        mockMvc.perform(patch("/products/reactivate/{productId}", productId)).andExpect(status().isNotFound());
    }

}
