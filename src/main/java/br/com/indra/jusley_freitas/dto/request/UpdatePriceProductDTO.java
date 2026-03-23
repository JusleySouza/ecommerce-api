package br.com.indra.jusley_freitas.dto.request;

import java.math.BigDecimal;

public record UpdatePriceProductDTO(
        BigDecimal price
) {}
