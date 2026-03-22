package br.com.indra.jusley_freitas.controller;

import br.com.indra.jusley_freitas.dto.request.ProductRequestDTO;
import br.com.indra.jusley_freitas.model.Product;
import br.com.indra.jusley_freitas.service.implement.ProductServiceImplement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductServiceImplement service;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequestDTO requestDTO){
        return new ResponseEntity<>(service.createProduct(requestDTO), HttpStatus.CREATED);
    }

}
