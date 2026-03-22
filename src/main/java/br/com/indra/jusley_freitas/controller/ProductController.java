package br.com.indra.jusley_freitas.controller;

import br.com.indra.jusley_freitas.dto.request.ProductRequestDTO;
import br.com.indra.jusley_freitas.dto.response.ProductResponseDTO;
import br.com.indra.jusley_freitas.model.Product;
import br.com.indra.jusley_freitas.service.implement.ProductServiceImplement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductServiceImplement service;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequestDTO requestDTO){
        return new ResponseEntity<>(service.createProduct(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping("{productId}")
    public ResponseEntity<ProductResponseDTO> getProductsById(@PathVariable UUID productId) {
        return new ResponseEntity<>(service.findProductById(productId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return new ResponseEntity<>(service.findAllProducts(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void>  deleteProduct(@PathVariable UUID id){
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
