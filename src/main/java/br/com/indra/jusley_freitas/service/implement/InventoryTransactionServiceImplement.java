package br.com.indra.jusley_freitas.service.implement;

import br.com.indra.jusley_freitas.config.LoggerConfig;
import br.com.indra.jusley_freitas.dto.request.inventory.InventoryTransactionRequestDTO;
import br.com.indra.jusley_freitas.dto.response.inventory.InventoryTransactionResponseDTO;
import br.com.indra.jusley_freitas.dto.response.inventory.CompleteInventoryTransactionResponseDTO;
import br.com.indra.jusley_freitas.enums.InventoryReason;
import br.com.indra.jusley_freitas.exception.InsufficientStockException;
import br.com.indra.jusley_freitas.exception.ResourceNotFoundException;
import br.com.indra.jusley_freitas.mapper.InventoryTransactionMapper;
import br.com.indra.jusley_freitas.model.InventoryTransaction;
import br.com.indra.jusley_freitas.model.Product;
import br.com.indra.jusley_freitas.repository.InventoryTransactionRepository;
import br.com.indra.jusley_freitas.repository.ProductRepository;
import br.com.indra.jusley_freitas.service.InventoryTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InventoryTransactionServiceImplement implements InventoryTransactionService {

    private final InventoryTransactionRepository repository;
    private final ProductRepository productRepository;

    @Transactional
    public InventoryTransactionResponseDTO addStock(UUID productId, InventoryTransactionRequestDTO request) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException("We were unable to find a product with this ID: " + productId));

        product.setStockQuantity(product.getStockQuantity() + request.quantity());

        InventoryTransaction transaction = InventoryTransactionMapper.toModel(productId, request);

        transaction.setDelta(request.quantity());

        productRepository.save(product);

        repository.save(transaction);

        LoggerConfig.LOGGER_INVENTORY_TRANSACTION.info("Stock added: " + transaction.getDelta() + " to product ID: " + productId + " successfully!");
        return InventoryTransactionMapper.toResponse(productId, product.getStockQuantity());
    }

    @Transactional
    public InventoryTransactionResponseDTO removeStock(UUID productId, InventoryTransactionRequestDTO request) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException("We were unable to find a product with this ID: " + productId));

        if (product.getStockQuantity() < request.quantity()) {
            throw new InsufficientStockException("Insufficient stock");
        }

        product.setStockQuantity(product.getStockQuantity() - request.quantity());

        InventoryTransaction transaction = InventoryTransactionMapper.toModel(productId, -request.quantity(), InventoryReason.SALE);

        productRepository.save(product);

        repository.save(transaction);

        LoggerConfig.LOGGER_INVENTORY_TRANSACTION.info("Stock removed: " + transaction.getDelta() + " to product ID: " + productId + " successfully!");
        return InventoryTransactionMapper.toResponse(productId, product.getStockQuantity());
    }

    public List<CompleteInventoryTransactionResponseDTO> getAllTransactions(UUID productId) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException("We were unable to find a product with this ID: " + productId));

        List<InventoryTransaction> inventoryTransactions = repository.findByProductId(productId);

        if (inventoryTransactions.isEmpty()) {
            throw new ResourceNotFoundException("No inventory transactions found for this product: " + productId);
        }

        LoggerConfig.LOGGER_INVENTORY_TRANSACTION.info("Stock retrieved for product ID: " + productId + " successfully!");
        return InventoryTransactionMapper.toResponseList(inventoryTransactions);
    }

}


