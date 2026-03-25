package br.com.indra.jusley_freitas.service;

import br.com.indra.jusley_freitas.dto.request.sub_category.SubCategoryRequestDTO;
import br.com.indra.jusley_freitas.dto.response.SubCategoryResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface SubCategoryService {

    public SubCategoryResponseDTO createSubCategory(UUID categoryId, SubCategoryRequestDTO requestDTO);

    public List<SubCategoryResponseDTO> findAllSubCategoriesByCategoryId(UUID categoryId);

}
