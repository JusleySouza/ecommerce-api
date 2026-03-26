package br.com.indra.jusley_freitas.service.implement;

import br.com.indra.jusley_freitas.dto.request.sub_category.SubCategoryRequestDTO;
import br.com.indra.jusley_freitas.dto.response.sub_category.SubCategoryResponseDTO;
import br.com.indra.jusley_freitas.exception.DuplicateSubCategoryException;
import br.com.indra.jusley_freitas.exception.ResourceNotFoundException;
import br.com.indra.jusley_freitas.model.Category;
import br.com.indra.jusley_freitas.model.SubCategory;
import br.com.indra.jusley_freitas.repository.CategoryRepository;
import br.com.indra.jusley_freitas.repository.SubCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubCategoryServiceImplementTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private SubCategoryRepository subCategoryRepository;

    @InjectMocks
    private SubCategoryServiceImplement service;

    private UUID categoryId;
    private UUID subCategoryId;
    private Category category;
    private SubCategory subCategory;
    private SubCategoryRequestDTO requestDTO;

    @BeforeEach
    void setup() {
        categoryId = UUID.randomUUID();
        subCategoryId = UUID.randomUUID();

        category = Category.builder()
                .id(categoryId)
                .name("Eletronicos")
                .build();

        subCategory = SubCategory.builder()
                .id(subCategoryId)
                .name("Celulares")
                .description("Smartphones")
                .category(category)
                .build();

        requestDTO = new SubCategoryRequestDTO(
                "Celulares",
                "Smartphones pre-lancados"
        );
    }

    @Test
    void shouldCreateSubCategory() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(subCategoryRepository.existsByNameAndCategoryId(anyString(), any())).thenReturn(false);

        SubCategoryResponseDTO response = service.createSubCategory(categoryId, requestDTO);

        assertNotNull(response);
        assertEquals("Celulares", response.name());

        verify(subCategoryRepository).save(any(SubCategory.class));
    }

    @Test
    void shouldThrowWhenCategoryNotFoundOnCreate() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.createSubCategory(categoryId, requestDTO));
    }

    @Test
    void shouldThrowWhenDuplicateSubCategory() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(subCategoryRepository.existsByNameAndCategoryId(anyString(), any())).thenReturn(true);

        assertThrows(DuplicateSubCategoryException.class, () -> service.createSubCategory(categoryId, requestDTO));
    }

    @Test
    void shouldReturnSubCategories() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(subCategoryRepository.findByCategoryId(categoryId)).thenReturn(List.of(subCategory));

        List<SubCategoryResponseDTO> result = service.findAllSubCategoriesByCategoryId(categoryId);

        assertEquals(1, result.size());
        assertEquals("Celulares", result.get(0).name());
    }

    @Test
    void shouldThrowWhenFindingSubCategoriesWithCategoryNotFound() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findAllSubCategoriesByCategoryId(categoryId));

        verify(subCategoryRepository, never()).findByCategoryId(any());
    }

    @Test
    void shouldThrowWhenNoSubCategoriesFound() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(subCategoryRepository.findByCategoryId(categoryId)).thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class, () -> service.findAllSubCategoriesByCategoryId(categoryId));
    }

    @Test
    void shouldUpdateSubCategory() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(subCategoryRepository.findById(subCategoryId)).thenReturn(Optional.of(subCategory));
        when(subCategoryRepository.existsByNameAndCategoryId(anyString(), any())).thenReturn(false);

        service.updateSubCategory(categoryId, subCategoryId, requestDTO);

        verify(subCategoryRepository).save(any(SubCategory.class));
    }

    @Test
    void shouldThrowWhenCategoryNotFoundOnUpdate() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.updateSubCategory(categoryId, subCategoryId, requestDTO));

        verify(subCategoryRepository, never()).findById(any());
        verify(subCategoryRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenSubCategoryNotFoundOnUpdate() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(subCategoryRepository.findById(subCategoryId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.updateSubCategory(categoryId, subCategoryId, requestDTO));
    }

    @Test
    void shouldThrowWhenDuplicateOnUpdate() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(subCategoryRepository.findById(subCategoryId)).thenReturn(Optional.of(subCategory));
        when(subCategoryRepository.existsByNameAndCategoryId(anyString(), any())).thenReturn(true);

        assertThrows(DuplicateSubCategoryException.class, () -> service.updateSubCategory(categoryId, subCategoryId, requestDTO));
    }

    @Test
    void shouldDeleteSubCategory() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(subCategoryRepository.findById(subCategoryId)).thenReturn(Optional.of(subCategory));

        service.deleteSubCategory(categoryId, subCategoryId);

        verify(subCategoryRepository).delete(subCategory);
    }

    @Test
    void shouldThrowWhenDeletingNonExistingCategory() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.deleteSubCategory(categoryId, subCategoryId));

        verify(subCategoryRepository, never()).findById(any());
        verify(subCategoryRepository, never()).delete(any());
    }

    @Test
    void shouldThrowWhenDeletingNonExistingSubCategory() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(subCategoryRepository.findById(subCategoryId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.deleteSubCategory(categoryId, subCategoryId));
    }
}
