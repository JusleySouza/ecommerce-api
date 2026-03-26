package br.com.indra.jusley_freitas.service.implement;

import br.com.indra.jusley_freitas.dto.request.category.CategoryRequestDTO;
import br.com.indra.jusley_freitas.dto.response.category.CategoryResponseDTO;
import br.com.indra.jusley_freitas.dto.response.category.CategoryWithProductsResponseDTO;
import br.com.indra.jusley_freitas.dto.response.sub_category.SubCategoryWithProductsDTO;
import br.com.indra.jusley_freitas.exception.DuplicateCategoryException;
import br.com.indra.jusley_freitas.exception.ResourceNotFoundException;
import br.com.indra.jusley_freitas.model.Category;
import br.com.indra.jusley_freitas.model.Product;
import br.com.indra.jusley_freitas.model.SubCategory;
import br.com.indra.jusley_freitas.repository.CategoryRepository;
import br.com.indra.jusley_freitas.repository.ProductRepository;
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
class CategoryServiceImplementTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CategoryServiceImplement service;

    private Category category;
    private CategoryRequestDTO requestDTO;
    private UUID categoryId;

    @BeforeEach
    void setup() {
        categoryId = UUID.randomUUID();

        requestDTO = new CategoryRequestDTO("Eletronicos");

        category = Category.builder()
                .id(categoryId)
                .name("Eletronicos")
                .build();
    }

    @Test
    void shouldCreateCategorySuccessfully() {
        when(categoryRepository.findByName(anyString())).thenReturn(null);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryResponseDTO response = service.createCategory(requestDTO);

        assertNotNull(response);
        assertEquals("Eletronicos", response.name());

        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void shouldThrowExceptionWhenCategoryNameAlreadyExists() {
        when(categoryRepository.findByName(anyString())).thenReturn(category);

        assertThrows(DuplicateCategoryException.class,
                () -> service.createCategory(requestDTO));

        verify(categoryRepository, never()).save(any());
    }

    @Test
    void shouldReturnAllCategories() {
        when(categoryRepository.findAll()).thenReturn(List.of(category));

        List<CategoryResponseDTO> result = service.findAllCategories();

        assertEquals(1, result.size());
        assertEquals("Eletronicos", result.get(0).name());
    }

    @Test
    void shouldUpdateCategorySuccessfully() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryRepository.findByName(anyString())).thenReturn(null);

        service.updateCategory(requestDTO, categoryId);

        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void shouldThrowWhenUpdatingNonExistingCategory() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.updateCategory(requestDTO, categoryId));
    }

    @Test
    void shouldThrowWhenUpdatingWithDuplicateName() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryRepository.findByName(anyString())).thenReturn(category);

        assertThrows(DuplicateCategoryException.class,
                () -> service.updateCategory(requestDTO, categoryId));
    }

    @Test
    void shouldDeleteCategorySuccessfully() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        service.deleteCategory(categoryId);

        verify(categoryRepository).delete(category);
    }

    @Test
    void shouldThrowWhenDeletingNonExistingCategory() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.deleteCategory(categoryId));
    }

    @Test
    void shouldReturnCategoryById() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        CategoryResponseDTO response = service.findByCategoryId(categoryId);

        assertNotNull(response);
        assertEquals("Eletronicos", response.name());
    }

    @Test
    void shouldThrowWhenCategoryNotFoundById() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.findByCategoryId(categoryId));
    }

    @Test
    void shouldReturnProductsGroupedByCategory() {
        UUID subCategoryId = UUID.randomUUID();

        SubCategory subCategory = new SubCategory();
        subCategory.setId(subCategoryId);
        subCategory.setName("Celulares");
        subCategory.setCategory(category);

        Product product = new Product();
        product.setSubCategory(subCategory);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(productRepository.findAllByCategoryId(categoryId)).thenReturn(List.of(product));

        CategoryWithProductsResponseDTO response = service.findAllProductsByCategory(categoryId);

        assertNotNull(response);
        assertEquals(category.getId(), response.id());
        assertEquals("Eletronicos", response.name());

        assertFalse(response.subCategories().isEmpty());
        assertEquals(1, response.subCategories().size());

        SubCategoryWithProductsDTO subDTO = response.subCategories().get(0);

        assertEquals(subCategoryId, subDTO.id());
        assertEquals("Celulares", subDTO.name());
    }

    @Test
    void shouldThrowWhenNoProductsFound() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(productRepository.findAllByCategoryId(categoryId)).thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class, () -> service.findAllProductsByCategory(categoryId));
    }

    @Test
    void shouldThrowWhenCategoryNotFoundInProductsSearch() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findAllProductsByCategory(categoryId));
    }
}
