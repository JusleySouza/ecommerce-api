package br.com.indra.jusley_freitas.service;

import br.com.indra.jusley_freitas.dto.request.product.ProductRequestDTO;
import br.com.indra.jusley_freitas.dto.request.product.UpdatePriceProductDTO;
import br.com.indra.jusley_freitas.dto.request.product.UpdateProductDTO;
import br.com.indra.jusley_freitas.dto.response.product.ProductResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ProductService {

    public ProductResponseDTO  createProduct(ProductRequestDTO requestDTO);

    public void updateProduct(UpdateProductDTO productDTO, UUID productId);

    public void updatePriceProduct(UpdatePriceProductDTO productDTO, UUID productId);

    public ProductResponseDTO findProductById(UUID productId);

    public ProductResponseDTO findByName(String name);

    public List<ProductResponseDTO> findAllProducts();

    public List<ProductResponseDTO> findAllProductsBySubCategoryId(UUID subCategoryId);

    public void deleteProduct(UUID productId);

    public void reactivateProduct(UUID productId);

}
