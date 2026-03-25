package br.com.indra.jusley_freitas.service.implement;

import br.com.indra.jusley_freitas.config.LoggerConfig;
import br.com.indra.jusley_freitas.dto.request.sub_category.SubCategoryRequestDTO;
import br.com.indra.jusley_freitas.dto.response.sub_category.SubCategoryResponseDTO;
import br.com.indra.jusley_freitas.exception.DuplicateCategoryException;
import br.com.indra.jusley_freitas.exception.ResourceNotFoundException;
import br.com.indra.jusley_freitas.mapper.SubCategoryMapper;
import br.com.indra.jusley_freitas.model.Category;
import br.com.indra.jusley_freitas.model.SubCategory;
import br.com.indra.jusley_freitas.repository.CategoryRepository;
import br.com.indra.jusley_freitas.repository.SubCategoryRepository;
import br.com.indra.jusley_freitas.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SubCategoryServiceImplement implements SubCategoryService {

    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    public SubCategoryResponseDTO createSubCategory(UUID categoryId, SubCategoryRequestDTO requestDTO) {
         Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                 new ResourceNotFoundException("We were unable to find a category with this ID: " + categoryId));

        SubCategory subCategory = SubCategoryMapper.toModel(requestDTO, category);
        hasDuplicateName(subCategory);
        subCategoryRepository.save(subCategory);

        LoggerConfig.LOGGER_SUB_CATEGORY.info("SubCategory: " + subCategory.getName() + " created successfully!");
        return SubCategoryMapper.toResponse(subCategory);
    }

    public List<SubCategoryResponseDTO> findAllSubCategoriesByCategoryId(UUID categoryId) {
        categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("We were unable to find a category with this ID: " + categoryId));

        List<SubCategory> subCategories = subCategoryRepository.findByCategoryId(categoryId);

        if (subCategories.isEmpty()) {
            throw new ResourceNotFoundException("No subcategories found for category ID: " + categoryId);
        }

        LoggerConfig.LOGGER_SUB_CATEGORY.info("SubCategory list successfully executed!");
        return SubCategoryMapper.toResponseList(subCategories);
    }

    public void updateSubCategory(UUID categoryId, UUID subCategoryId, SubCategoryRequestDTO requestDTO) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("We were unable to find a category with this ID: " + categoryId));

        SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElseThrow(() ->
                new ResourceNotFoundException("We were unable to find a subCategory with this ID: " + subCategoryId));

        SubCategoryMapper.updateEntity(subCategory, requestDTO, category);
        hasDuplicateName(subCategory);

        LoggerConfig.LOGGER_SUB_CATEGORY.info("SubCategory data: " + subCategory.getName() + " updated successfully!");
        subCategoryRepository.save(subCategory);
    }

    public void deleteSubCategory(UUID categoryId, UUID subCategoryId) {
        categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("We were unable to find a category with this ID: " + categoryId));

        SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElseThrow(() ->
                new ResourceNotFoundException("We were unable to find a subCategory with this ID: " + subCategoryId));

        LoggerConfig.LOGGER_SUB_CATEGORY.info("SubCategory data: " + subCategory.getName() + " deleted successfully!");
        subCategoryRepository.delete(subCategory);
    }


    private void hasDuplicateName(SubCategory subCategory) {
        boolean exists = subCategoryRepository.existsByNameAndCategoryId(subCategory.getName(), subCategory.getCategory().getId());
        if (exists) {
            throw new DuplicateCategoryException("SubCategory already exists in this category with name: " + subCategory.getName());
        }
    }

}
