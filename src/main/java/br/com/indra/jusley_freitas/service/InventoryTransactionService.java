package br.com.indra.jusley_freitas.service;

import br.com.indra.jusley_freitas.dto.request.inventory.InventoryTransactionRequestDTO;
import br.com.indra.jusley_freitas.dto.response.inventory.InventoryTransactionResponseDTO;
import br.com.indra.jusley_freitas.dto.response.inventory.CompleteInventoryTransactionResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface InventoryTransactionService {

    InventoryTransactionResponseDTO addStock(UUID productId, InventoryTransactionRequestDTO request);

    InventoryTransactionResponseDTO removeStock(UUID productId, InventoryTransactionRequestDTO request);

    public List<CompleteInventoryTransactionResponseDTO> getAllTransactions(UUID productId);

}
