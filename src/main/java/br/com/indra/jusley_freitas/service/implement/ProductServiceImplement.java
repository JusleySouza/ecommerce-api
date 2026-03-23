package br.com.indra.jusley_freitas.service.implement;

import br.com.indra.jusley_freitas.dto.request.ProductRequestDTO;
import br.com.indra.jusley_freitas.dto.request.UpdateProductDTO;
import br.com.indra.jusley_freitas.dto.response.ProductResponseDTO;
import br.com.indra.jusley_freitas.mapper.ProductMapper;
import br.com.indra.jusley_freitas.model.Product;
import br.com.indra.jusley_freitas.repository.ProductRepository;
import br.com.indra.jusley_freitas.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductServiceImplement implements ProductService {

    private final ProductRepository productRepository;

    private Product product;
    private List<Product> products;
    private ProductResponseDTO responseDTO;
    private List<ProductResponseDTO> listResponse;

    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO){
        Product product = ProductMapper.toEntity(requestDTO);
        productRepository.save(product);
        return ProductMapper.toResponse(product);
    }

    public void updateProduct(UpdateProductDTO updateProductDTO, UUID productId){
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        product = ProductMapper.updateEntity(product, updateProductDTO);
        productRepository.save(product);
    }

    public ProductResponseDTO findProductById(UUID productId){
        Product product = productRepository.findById(productId).get();

        if(product == null){
            throw new RuntimeException("Product not found");
        }

        responseDTO = ProductMapper.toResponse(product);

        return responseDTO;
    }

    public List<ProductResponseDTO> findAllProducts(){
        listResponse = new ArrayList<>();

        products = productRepository.findAll();

        for (Product product : products) {
            listResponse.add(ProductMapper.toResponse(product));
        }
        return listResponse;
    }

    public void deleteProduct(UUID productId){
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        product = ProductMapper.deleteProduct(product);
        productRepository.save(product);
    }

}
