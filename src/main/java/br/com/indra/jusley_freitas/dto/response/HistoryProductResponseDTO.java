package br.com.indra.jusley_freitas.dto.response;

import br.com.indra.jusley_freitas.model.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record HistoryProductResponseDTO(
        UUID id,
        String product,
        BigDecimal oldPrice,
        BigDecimal newPrice,
        LocalDateTime dateOfChange
) {}
