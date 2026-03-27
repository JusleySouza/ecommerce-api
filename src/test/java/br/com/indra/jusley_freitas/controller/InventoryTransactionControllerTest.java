package br.com.indra.jusley_freitas.controller;

import br.com.indra.jusley_freitas.dto.request.inventory.InventoryTransactionRequestDTO;
import br.com.indra.jusley_freitas.dto.response.inventory.CompleteInventoryTransactionResponseDTO;
import br.com.indra.jusley_freitas.dto.response.inventory.InventoryTransactionResponseDTO;
import br.com.indra.jusley_freitas.enums.InventoryReason;
import br.com.indra.jusley_freitas.exception.InsufficientStockException;
import br.com.indra.jusley_freitas.exception.ResourceNotFoundException;
import br.com.indra.jusley_freitas.service.InventoryTransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(InventoryTransactionController.class)
class InventoryTransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryTransactionService service;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID productId;
    private InventoryTransactionRequestDTO requestDTO;
    private InventoryTransactionResponseDTO responseDTO;
    private CompleteInventoryTransactionResponseDTO completeResponseDTO;

    @BeforeEach
    void setup() {
        productId = UUID.randomUUID();

        requestDTO = new InventoryTransactionRequestDTO(10, "PURCHASE");

        responseDTO = new InventoryTransactionResponseDTO(productId, 100);

        completeResponseDTO = new CompleteInventoryTransactionResponseDTO(
                UUID.randomUUID(),
                productId,
                10,
                InventoryReason.PURCHASE,
                null,
                null,
                LocalDateTime.now()
        );
    }

    @Test
    void shouldAddStock() throws Exception {
        when(service.addStock(eq(productId), any())).thenReturn(responseDTO);

        mockMvc.perform(post("/inventory/{productId}/add", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.currentStock").value(100));

        verify(service).addStock(eq(productId), any());
    }

    @Test
    void shouldRemoveStock() throws Exception {
        when(service.removeStock(eq(productId), any())).thenReturn(responseDTO);

        mockMvc.perform(post("/inventory/{productId}/remove", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.currentStock").value(100));

        verify(service).removeStock(eq(productId), any());
    }

    @Test
    void shouldReturnAllTransactions() throws Exception {
        when(service.getAllTransactions(productId)).thenReturn(List.of(completeResponseDTO));

        mockMvc.perform(get("/inventory/{productId}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value(productId.toString()));
    }

    @Test
    void shouldReturnBadRequestWhenInvalidInput() throws Exception {
        InventoryTransactionRequestDTO invalid = new InventoryTransactionRequestDTO(-1, "");

        mockMvc.perform(post("/inventory/{productId}/add", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnNotFoundWhenProductDoesNotExist() throws Exception {
        when(service.getAllTransactions(productId)).thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/inventory/{productId}", productId)).andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnUnprocessableEntityWhenInsufficientStock() throws Exception {
        when(service.removeStock(eq(productId), any())).thenThrow(new InsufficientStockException("Insufficient stock"));

        mockMvc.perform(post("/inventory/{productId}/remove", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isUnprocessableEntity());
    }
}
