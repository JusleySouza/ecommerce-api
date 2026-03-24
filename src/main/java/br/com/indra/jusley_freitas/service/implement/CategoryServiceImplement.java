package br.com.indra.jusley_freitas.service.implement;

import br.com.indra.jusley_freitas.config.LoggerConfig;
import br.com.indra.jusley_freitas.dto.request.category.CategoryRequestDTO;
import br.com.indra.jusley_freitas.dto.response.CategoryResponseDTO;
import br.com.indra.jusley_freitas.exception.DuplicateCategoryException;
import br.com.indra.jusley_freitas.mapper.CategoryMapper;
import br.com.indra.jusley_freitas.model.Category;
import br.com.indra.jusley_freitas.repository.CategoryRepository;
import br.com.indra.jusley_freitas.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryServiceImplement implements CategoryService {

    private final CategoryRepository categoryRepository;
    private List<CategoryResponseDTO> listResponse;

    public CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO) {
        Category categoryEntity = categoryRepository.findByName(requestDTO.name());

        if(categoryEntity != null) {
            throw new DuplicateCategoryException("Could not register category. " +
                    "There is already a category registered with this name: " + categoryEntity.getName());
        }

        Category category = CategoryMapper.toModel(requestDTO);
        categoryRepository.save(category);

        LoggerConfig.LOGGER_CATEGORY.info("Category: " + category.getName() + " created successfully!");
        return CategoryMapper.toResponse(category);
    }

    public List<CategoryResponseDTO> findAllCategories() {
        listResponse = new ArrayList<>();
        List<Category> categories = categoryRepository.findAll();

        for(Category category : categories) {
            listResponse.add(CategoryMapper.toResponse(category));
        }

        LoggerConfig.LOGGER_CATEGORY.info("Category list successfully executed!");
        return listResponse;
    }

}
