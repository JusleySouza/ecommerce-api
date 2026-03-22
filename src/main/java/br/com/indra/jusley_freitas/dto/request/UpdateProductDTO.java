package br.com.indra.jusley_freitas.dto.request;

public record UpdateProductDTO(
        String name,
        String description,
        int stockQuantity
) {}
