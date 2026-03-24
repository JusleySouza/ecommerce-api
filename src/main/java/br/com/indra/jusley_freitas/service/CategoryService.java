package br.com.indra.jusley_freitas.service;

import br.com.indra.jusley_freitas.dto.request.category.CategoryRequestDTO;
import br.com.indra.jusley_freitas.dto.response.CategoryResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {

    public CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO);

}
