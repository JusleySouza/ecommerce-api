package br.com.indra.jusley_freitas.mapper;

import br.com.indra.jusley_freitas.dto.request.inventory.InventoryTransactionRequestDTO;
import br.com.indra.jusley_freitas.dto.response.inventory.InventoryTransactionResponseDTO;
import br.com.indra.jusley_freitas.dto.response.inventory.CompleteInventoryTransactionResponseDTO;
import br.com.indra.jusley_freitas.enums.InventoryReason;
import br.com.indra.jusley_freitas.model.InventoryTransaction;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class InventoryTransactionMapper {

    public static InventoryTransaction toModel(UUID productId, InventoryTransactionRequestDTO dto) {
        return InventoryTransaction.builder()
                .productId(productId)
                .delta(dto.quantity())
                .reason(InventoryReason.valueOf(dto.reason()))
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static InventoryTransaction toModel(UUID productId, int delta, InventoryReason reason) {
        return InventoryTransaction.builder()
                .productId(productId)
                .delta(delta)
                .reason(reason)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static InventoryTransactionResponseDTO toResponse(UUID productId, int stock) {
        return new InventoryTransactionResponseDTO(
                productId,
                stock
        );
    }

    public static CompleteInventoryTransactionResponseDTO toResponseInventory(InventoryTransaction entity) {
        return new CompleteInventoryTransactionResponseDTO(
                entity.getId(),
                entity.getProductId(),
                entity.getDelta(),
                entity.getReason(),
                entity.getReferenceID(),
                entity.getCreatedBy(),
                entity.getCreatedAt()
        );
    }

    public static List<CompleteInventoryTransactionResponseDTO> toResponseList(List<InventoryTransaction> inventaryTransaction) {
        if (inventaryTransaction == null || inventaryTransaction.isEmpty()) {
            return Collections.emptyList();
        }
        return inventaryTransaction.stream()
                .map(InventoryTransactionMapper::toResponseInventory)
                .toList();
    }
}

