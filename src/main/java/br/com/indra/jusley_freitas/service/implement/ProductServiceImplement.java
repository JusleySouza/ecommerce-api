package br.com.indra.jusley_freitas.service.implement;

import br.com.indra.jusley_freitas.dto.request.ProductRequestDTO;
import br.com.indra.jusley_freitas.dto.response.ProductResponseDTO;
import br.com.indra.jusley_freitas.mapper.ProductMapper;
import br.com.indra.jusley_freitas.model.Product;
import br.com.indra.jusley_freitas.repository.ProductRepository;
import br.com.indra.jusley_freitas.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductServiceImplement implements ProductService {

    private final ProductRepository productRepository;

    private Product product;
    private List<Product> products;
    private ProductResponseDTO responseDTO;
    private List<ProductResponseDTO> listResponse;

    public Product createProduct(ProductRequestDTO requestDTO){
        product = ProductMapper.toModel(requestDTO);
        return productRepository.save(product);
    }

}
