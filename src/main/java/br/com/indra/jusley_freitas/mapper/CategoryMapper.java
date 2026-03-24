package br.com.indra.jusley_freitas.mapper;

import br.com.indra.jusley_freitas.dto.request.category.CategoryRequestDTO;
import br.com.indra.jusley_freitas.dto.response.CategoryResponseDTO;
import br.com.indra.jusley_freitas.model.Category;

import java.time.LocalDateTime;

public class CategoryMapper {

    public static Category toModel(CategoryRequestDTO requestDTO) {
        return Category.builder()
                .name(requestDTO.name())
                .build();
    }

    public static CategoryResponseDTO toResponse(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }

    public static Category updateEntity(Category entity, CategoryRequestDTO dto) {
        entity.setName(dto.name());
        entity.setUpdatedAt(LocalDateTime.now());
        return entity;
    }

}
