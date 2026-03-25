package br.com.indra.jusley_freitas.service;

import br.com.indra.jusley_freitas.dto.request.category.CategoryRequestDTO;
import br.com.indra.jusley_freitas.dto.response.category.CategoryResponseDTO;
import br.com.indra.jusley_freitas.dto.response.category.CategoryWithProductsResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CategoryService {

    public CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO);

    public List<CategoryResponseDTO> findAllCategories();

    public void updateCategory(CategoryRequestDTO requestDTO, UUID categoryId);

    public void deleteCategory(UUID categoryId);

    public CategoryResponseDTO findByCategoryId(UUID categoryId);

    public CategoryWithProductsResponseDTO findAllProductsByCategory(UUID categoryId);

}
