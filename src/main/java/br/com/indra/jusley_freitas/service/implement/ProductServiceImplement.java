package br.com.indra.jusley_freitas.service.implement;

import br.com.indra.jusley_freitas.config.LoggerConfig;
import br.com.indra.jusley_freitas.dto.request.product.ProductRequestDTO;
import br.com.indra.jusley_freitas.dto.request.product.UpdatePriceProductDTO;
import br.com.indra.jusley_freitas.dto.request.product.UpdateProductDTO;
import br.com.indra.jusley_freitas.dto.response.product.ProductResponseDTO;
import br.com.indra.jusley_freitas.exception.UpdatedNotAllowedException;
import br.com.indra.jusley_freitas.exception.DuplicateSkuException;
import br.com.indra.jusley_freitas.exception.ResourceNotFoundException;
import br.com.indra.jusley_freitas.exception.ValidationException;
import br.com.indra.jusley_freitas.mapper.PriceHistoryMapper;
import br.com.indra.jusley_freitas.mapper.ProductMapper;
import br.com.indra.jusley_freitas.model.PriceHistory;
import br.com.indra.jusley_freitas.model.Product;
import br.com.indra.jusley_freitas.model.SubCategory;
import br.com.indra.jusley_freitas.repository.PriceHistoryRepository;
import br.com.indra.jusley_freitas.repository.ProductRepository;
import br.com.indra.jusley_freitas.repository.SubCategoryRepository;
import br.com.indra.jusley_freitas.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductServiceImplement implements ProductService {

    private final ProductRepository productRepository;
    private final PriceHistoryRepository priceRepository;
    private final SubCategoryRepository subCategoryRepository;
    private List<ProductResponseDTO> listResponse;

    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO){
        SubCategory subCategory = subCategoryRepository.findById(requestDTO.subCategory()).orElseThrow(() ->
                new ResourceNotFoundException("We could not find a sub_category with this ID: " + requestDTO.subCategory()));

        Product productEntity = productRepository.findBySku(requestDTO.sku());
        if(productEntity != null) {
            throw new DuplicateSkuException("Could not register product. There is already a product registered with this sku: " + requestDTO.sku());
        }

        Product product = ProductMapper.toEntity(requestDTO, subCategory);
        productRepository.save(product);

        LoggerConfig.LOGGER_PRODUCT.info("Product: " + product.getName() + " created successfully!");
        return ProductMapper.toResponse(product);
    }

    public void updateProduct(UpdateProductDTO updateProductDTO, UUID productId){
        SubCategory subCategory = subCategoryRepository.findById(updateProductDTO.subCategory()).orElseThrow(() ->
                new ResourceNotFoundException("We could not find a sub_category with this ID: " + updateProductDTO.subCategory()));

        Product product = productRepository.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException("We were unable to find a product with this ID: " + productId));

        product = ProductMapper.updateEntity(product, updateProductDTO, subCategory);

        LoggerConfig.LOGGER_PRODUCT.info("Product data: " + product.getName() + " updated successfully!");
        productRepository.save(product);
    }

    @Transactional
    public void updatePriceProduct(UpdatePriceProductDTO productDTO, UUID productId){
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new ResourceNotFoundException("We were unable to find a product with this ID: " + productId));

        if (product.getPrice().compareTo(productDTO.price()) != 0) {
            PriceHistory history = PriceHistoryMapper.toEntity( product, productDTO);
            priceRepository.save(history);
        }

        product = ProductMapper.updatePriceProduct(product, productDTO);

        LoggerConfig.LOGGER_PRODUCT.info("Product prices: " + product.getName() + " updated successfully!");
        productRepository.saveAndFlush(product);
    }

    public ProductResponseDTO findProductById(UUID productId){
        Product product = productRepository.findByIdAndActiveTrue(productId);
            if(product == null) {
               throw new ResourceNotFoundException("We were unable to find a product with this ID: " + productId);
            }

        LoggerConfig.LOGGER_PRODUCT.info("Product: " + product.getName() + " returned successfully!");
        return ProductMapper.toResponse(product);
    }

    public ProductResponseDTO findByName(String name){
        if (name.length() < 2) {
            throw new ValidationException("The name must be at least 2 characters long.");
        }

        Product product = productRepository.findByName(name);

        if (product == null) {
            throw new ResourceNotFoundException("Could not find a product with this name: " + name);
        }

        LoggerConfig.LOGGER_PRODUCT.info("Product: " + product.getName() + " returned successfully!");
        return ProductMapper.toResponse(product);
    }

    public List<ProductResponseDTO> findAllProducts(){
        listResponse = new ArrayList<>();

        List<Product> products = productRepository.findAllByActiveTrue();

        for (Product product : products) {
            listResponse.add(ProductMapper.toResponse(product));
        }

        LoggerConfig.LOGGER_PRODUCT.info("Product list successfully executed!");
        return listResponse;
    }

    public List<ProductResponseDTO> findAllProductsBySubCategoryId(UUID subCategoryId) {
        subCategoryRepository.findById(subCategoryId).orElseThrow(() ->
                new ResourceNotFoundException("We were unable to find a subcategory with this ID: " + subCategoryId));

        listResponse = new ArrayList<>();
        List<Product> products = productRepository.findBySubCategoryId(subCategoryId);

        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found for subcategory ID: " + subCategoryId);
        }

        LoggerConfig.LOGGER_PRODUCT.info("Product list successfully executed!");
        return ProductMapper.toResponseList(products);
    }

    public void deleteProduct(UUID productId){
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException("We were unable to find a product with this ID: " + productId));

        if(product.getActive() == Boolean.FALSE) {
            throw new UpdatedNotAllowedException("This product is already inactive.");
        }

        product = ProductMapper.deleteProduct(product);

        LoggerConfig.LOGGER_PRODUCT.info("Product data: " + product.getName() + " deleted successfully!");
        productRepository.save(product);
    }

    public void reactivateProduct(UUID productId){
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException("We were unable to find a product with this ID: " + productId));

        if(product.getActive() == Boolean.TRUE) {
            throw new UpdatedNotAllowedException("This product is already active.");
        }

        product = ProductMapper.reactivateProduct(product);

        LoggerConfig.LOGGER_PRODUCT.info("Product data: " + product.getName() + " activated successfully!");
        productRepository.save(product);
    }

}
