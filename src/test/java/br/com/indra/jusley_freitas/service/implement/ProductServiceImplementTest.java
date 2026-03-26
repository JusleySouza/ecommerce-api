package br.com.indra.jusley_freitas.service.implement;

import br.com.indra.jusley_freitas.dto.request.product.ProductRequestDTO;
import br.com.indra.jusley_freitas.dto.request.product.UpdatePriceProductDTO;
import br.com.indra.jusley_freitas.dto.request.product.UpdateProductDTO;
import br.com.indra.jusley_freitas.dto.response.product.ProductResponseDTO;
import br.com.indra.jusley_freitas.exception.DuplicateSkuException;
import br.com.indra.jusley_freitas.exception.ResourceNotFoundException;
import br.com.indra.jusley_freitas.exception.UpdatedNotAllowedException;
import br.com.indra.jusley_freitas.exception.ValidationException;
import br.com.indra.jusley_freitas.model.PriceHistory;
import br.com.indra.jusley_freitas.model.Product;
import br.com.indra.jusley_freitas.model.SubCategory;
import br.com.indra.jusley_freitas.repository.PriceHistoryRepository;
import br.com.indra.jusley_freitas.repository.ProductRepository;
import br.com.indra.jusley_freitas.repository.SubCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplementTest {

    @InjectMocks
    private ProductServiceImplement service;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PriceHistoryRepository priceRepository;

    @Mock
    private SubCategoryRepository subCategoryRepository;

    private UUID productId;
    private UUID subCategoryId;
    private SubCategory subCategory;
    private Product product;
    private ProductRequestDTO productRequestDTO;
    private UpdateProductDTO updateProductDTO;

    @BeforeEach
    void setup() {
        productId = UUID.randomUUID();
        subCategoryId = UUID.randomUUID();

        subCategory = new SubCategory();
        subCategory.setId(subCategoryId);

        product = Product.builder()
                .id(productId)
                .name("Samsung")
                .price(new BigDecimal("100"))
                .active(true)
                .subCategory(subCategory)
                .build();

        productRequestDTO = new ProductRequestDTO(
                "Samsung",
                "Samsung Pro Max",
                "SKU123",
                new BigDecimal("100"),
                new BigDecimal("80"),
                10,
                subCategoryId
        );

        updateProductDTO = new UpdateProductDTO(
                "Samsung Atualizado",
                "Samsung Pro Max atualizado",
                20,
                subCategoryId
        );
    }

    @Test
    void shouldCreateProduct() {
        when(subCategoryRepository.findById(subCategoryId)).thenReturn(Optional.of(subCategory));

        when(productRepository.findBySku("SKU123")).thenReturn(null);

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProductResponseDTO response = service.createProduct(productRequestDTO);

        assertNotNull(response);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldThrowWhenSubCategoryNotFound() {
        ProductRequestDTO dto = mock(ProductRequestDTO.class);

        when(dto.subCategory()).thenReturn(subCategoryId);
        when(subCategoryRepository.findById(subCategoryId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.createProduct(dto));
    }

    @Test
    void shouldThrowWhenSkuAlreadyExists() {
        ProductRequestDTO dto = mock(ProductRequestDTO.class);

        when(dto.subCategory()).thenReturn(subCategoryId);
        when(dto.sku()).thenReturn("SKU123");

        when(subCategoryRepository.findById(subCategoryId)).thenReturn(Optional.of(subCategory));

        when(productRepository.findBySku("SKU123")).thenReturn(product);

        assertThrows(DuplicateSkuException.class, () -> service.createProduct(dto));
    }

    @Test
    void shouldCreatePriceHistoryWhenPriceChanges() {
        UpdatePriceProductDTO dto = new UpdatePriceProductDTO(new BigDecimal("200"));

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        service.updatePriceProduct(dto, productId);

        verify(priceRepository).save(any(PriceHistory.class));
        verify(productRepository).saveAndFlush(any(Product.class));
    }

    @Test
    void shouldNotCreateHistoryWhenPriceIsSame() {
        UpdatePriceProductDTO dto = new UpdatePriceProductDTO(new BigDecimal("100"));

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        service.updatePriceProduct(dto, productId);

        verify(priceRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenDeletingInactiveProduct() {
        product.setActive(false);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        assertThrows(UpdatedNotAllowedException.class, () -> service.deleteProduct(productId));
    }

    @Test
    void shouldUpdateProductSuccessfully() {
        when(subCategoryRepository.findById(subCategoryId)).thenReturn(Optional.of(subCategory));

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        service.updateProduct(updateProductDTO, productId);

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldThrowWhenUpdatingProductWithInvalidSubCategory() {
        UpdateProductDTO dto = mock(UpdateProductDTO.class);

        when(dto.subCategory()).thenReturn(subCategoryId);
        when(subCategoryRepository.findById(subCategoryId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.updateProduct(dto, productId));
    }

    @Test
    void shouldThrowWhenUpdatingNonExistingProduct() {
        UpdateProductDTO dto = mock(UpdateProductDTO.class);

        when(dto.subCategory()).thenReturn(subCategoryId);

        when(subCategoryRepository.findById(subCategoryId)).thenReturn(Optional.of(subCategory));

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.updateProduct(dto, productId));
    }

    @Test
    void shouldReturnProductById() {
        when(productRepository.findByIdAndActiveTrue(productId)).thenReturn(product);

        ProductResponseDTO response = service.findProductById(productId);

        assertNotNull(response);
    }

    @Test
    void shouldThrowWhenProductNotFoundById() {
        when(productRepository.findByIdAndActiveTrue(productId)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> service.findProductById(productId));
    }

    @Test
    void shouldReturnProductWhenNameExists() {
        when(productRepository.findByName("Samsung")).thenReturn(product);

        ProductResponseDTO response = service.findByName("Samsung");

        assertNotNull(response);
        assertEquals("Samsung", response.name());

        verify(productRepository).findByName("Samsung");
    }

    @Test
    void shouldThrowValidationExceptionWhenNameIsTooShort() {
        assertThrows(ValidationException.class, () -> service.findByName("A"));

        verify(productRepository, never()).findByName(any());
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        when(productRepository.findByName("Samsung")).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> service.findByName("Samsung"));

        verify(productRepository).findByName("Samsung");
    }

    @Test
    void shouldReturnAllProducts() {
        when(productRepository.findAllByActiveTrue()).thenReturn(List.of(product));

        List<ProductResponseDTO> response = service.findAllProducts();

        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
    }

    @Test
    void shouldReturnEmptyListWhenNoProducts() {
        when(productRepository.findAllByActiveTrue()).thenReturn(List.of());

        List<ProductResponseDTO> response = service.findAllProducts();

        assertTrue(response.isEmpty());
    }

    @Test
    void shouldReturnProductsBySubCategory() {
        when(subCategoryRepository.findById(subCategoryId)).thenReturn(Optional.of(subCategory));

        when(productRepository.findBySubCategoryId(subCategoryId)).thenReturn(List.of(product));

        List<ProductResponseDTO> response = service.findAllProductsBySubCategoryId(subCategoryId);

        assertEquals(1, response.size());
    }

    @Test
    void shouldThrowWhenSubCategoryNotFoundInFindAll() {
        when(subCategoryRepository.findById(subCategoryId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findAllProductsBySubCategoryId(subCategoryId));
    }

    @Test
    void shouldThrowWhenNoProductsFoundBySubCategory() {
        when(subCategoryRepository.findById(subCategoryId)).thenReturn(Optional.of(subCategory));

        when(productRepository.findBySubCategoryId(subCategoryId)).thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class, () -> service.findAllProductsBySubCategoryId(subCategoryId));
    }

    @Test
    void shouldDeleteProductSuccessfully() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        service.deleteProduct(productId);

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldThrowWhenDeletingNonExistingProduct() {
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.deleteProduct(productId));
    }

    @Test
    void shouldReactivateProductSuccessfully() {
        product.setActive(false);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        service.reactivateProduct(productId);

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldThrowWhenReactivatingNonExistingProduct() {
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.reactivateProduct(productId));
    }

    @Test
    void shouldThrowExceptionWhenReactivatingAlreadyActiveProduct() {
        product.setActive(true);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        assertThrows(UpdatedNotAllowedException.class, () -> service.reactivateProduct(productId));

        verify(productRepository, never()).save(any());
    }

}
