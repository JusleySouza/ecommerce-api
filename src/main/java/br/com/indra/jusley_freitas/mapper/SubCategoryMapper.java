package br.com.indra.jusley_freitas.mapper;

import br.com.indra.jusley_freitas.dto.request.sub_category.SubCategoryRequestDTO;
import br.com.indra.jusley_freitas.dto.response.SubCategoryResponseDTO;
import br.com.indra.jusley_freitas.model.Category;
import br.com.indra.jusley_freitas.model.SubCategory;

public class SubCategoryMapper {

    public static SubCategory toModel(SubCategoryRequestDTO requestDTO, Category category) {
        return SubCategory.builder()
                .name(requestDTO.name())
                .description(requestDTO.description())
                .category(category)
                .build();
    }

    public static SubCategoryResponseDTO toResponse(SubCategory subCategory) {
        return new SubCategoryResponseDTO(
                subCategory.getId(),
                subCategory.getName(),
                subCategory.getDescription(),
                subCategory.getCreatedAt(),
                subCategory.getUpdatedAt(),
                subCategory.getCategory().getId()
        );
    }

    public static SubCategory updateEntity(SubCategory subCategory, SubCategoryRequestDTO requestDTO, Category category) {
        subCategory.setName(requestDTO.name());
        subCategory.setDescription(requestDTO.description());
        subCategory.setCategory(category);
        return subCategory;
    }

}
