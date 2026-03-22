package br.com.indra.jusley_freitas.service;

import br.com.indra.jusley_freitas.dto.request.ProductRequestDTO;
import br.com.indra.jusley_freitas.dto.response.ProductResponseDTO;
import br.com.indra.jusley_freitas.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ProductService {

    public Product createProduct(ProductRequestDTO requestDTO);

    public ProductResponseDTO findProductById(UUID id);

    public List<ProductResponseDTO> findAllProducts();

}
