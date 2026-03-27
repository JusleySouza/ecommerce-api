package br.com.indra.jusley_freitas.controller;

import br.com.indra.jusley_freitas.dto.request.inventory.InventoryTransactionRequestDTO;
import br.com.indra.jusley_freitas.dto.response.inventory.InventoryTransactionResponseDTO;
import br.com.indra.jusley_freitas.dto.response.inventory.CompleteInventoryTransactionResponseDTO;
import br.com.indra.jusley_freitas.service.InventoryTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
@Tag(name = "Inventory Transaction", description = "Endpoints for managing inventory transactions")
public class InventoryTransactionController {

    private final InventoryTransactionService service;

    @PostMapping("/{productId}/add")
    @Operation(summary = "Add stock", description = "Add stock to a product's inventory.")
    public ResponseEntity<InventoryTransactionResponseDTO> add(@PathVariable UUID productId, @Valid @RequestBody InventoryTransactionRequestDTO request) {
        return new ResponseEntity<>(service.addStock(productId, request), HttpStatus.CREATED);
    }

    @PostMapping("/{productId}/remove")
    @Operation(summary = "Remove stock", description = "Remove stock to a product's inventory.")
    public ResponseEntity<InventoryTransactionResponseDTO> remove(@PathVariable UUID productId, @Valid @RequestBody InventoryTransactionRequestDTO request) {
        return new ResponseEntity<>(service.removeStock(productId, request), HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Search the inventory by product ID.", description = "Returns the inventory data for the specified product.")
    public ResponseEntity<List<CompleteInventoryTransactionResponseDTO>> getAllTransactions(@PathVariable UUID productId) {
        return new ResponseEntity<>(service.getAllTransactions(productId), HttpStatus.OK);
    }

}
