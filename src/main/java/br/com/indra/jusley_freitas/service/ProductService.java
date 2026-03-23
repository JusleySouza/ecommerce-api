package br.com.indra.jusley_freitas.service;

import br.com.indra.jusley_freitas.dto.request.ProductRequestDTO;
import br.com.indra.jusley_freitas.dto.request.UpdatePriceProductDTO;
import br.com.indra.jusley_freitas.dto.request.UpdateProductDTO;
import br.com.indra.jusley_freitas.dto.response.ProductResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ProductService {

    public ProductResponseDTO  createProduct(ProductRequestDTO requestDTO);

    public void updateProduct(UpdateProductDTO productDTO, UUID productId);

    public void updatePriceProduct(UpdatePriceProductDTO productDTO, UUID productId);

    public ProductResponseDTO findProductById(UUID productId);

    public List<ProductResponseDTO> findAllProducts();

    public void deleteProduct(UUID productId);

}
