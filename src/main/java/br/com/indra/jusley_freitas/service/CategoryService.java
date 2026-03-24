package br.com.indra.jusley_freitas.service;

import br.com.indra.jusley_freitas.dto.request.category.CategoryRequestDTO;
import br.com.indra.jusley_freitas.dto.response.CategoryResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    public CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO);

    public List<CategoryResponseDTO> findAllCategories();

}
