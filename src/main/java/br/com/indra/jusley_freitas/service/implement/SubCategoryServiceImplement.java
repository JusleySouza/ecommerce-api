package br.com.indra.jusley_freitas.service.implement;

import br.com.indra.jusley_freitas.config.LoggerConfig;
import br.com.indra.jusley_freitas.dto.request.sub_category.SubCategoryRequestDTO;
import br.com.indra.jusley_freitas.dto.response.SubCategoryResponseDTO;
import br.com.indra.jusley_freitas.exception.DuplicateCategoryException;
import br.com.indra.jusley_freitas.mapper.SubCategoryMapper;
import br.com.indra.jusley_freitas.model.Category;
import br.com.indra.jusley_freitas.model.SubCategory;
import br.com.indra.jusley_freitas.repository.CategoryRepository;
import br.com.indra.jusley_freitas.repository.SubCategoryRepository;
import br.com.indra.jusley_freitas.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SubCategoryServiceImplement implements SubCategoryService {

    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    public SubCategoryResponseDTO createSubCategory(UUID categoryId, SubCategoryRequestDTO requestDTO) {
         Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                 new RuntimeException("We were unable to find a category with this ID: " + categoryId));

        SubCategory subCategory = SubCategoryMapper.toModel(requestDTO, category);
        hasDuplicateName(subCategory);
        subCategoryRepository.save(subCategory);

        LoggerConfig.LOGGER_SUB_CATEGORY.info("SubCategory: " + subCategory.getName() + " created successfully!");
        return SubCategoryMapper.toResponse(subCategory);
    }

    private void hasDuplicateName(SubCategory subCategory) {
        boolean exists = subCategoryRepository.existsByNameAndCategoryId(subCategory.getName(), subCategory.getCategory().getId());
        if (exists) {
            throw new DuplicateCategoryException("SubCategory with this name already exists in this category");
        }
    }

}
