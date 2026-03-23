package br.com.indra.jusley_freitas.controller;

import br.com.indra.jusley_freitas.dto.request.ProductRequestDTO;
import br.com.indra.jusley_freitas.dto.request.UpdatePriceProductDTO;
import br.com.indra.jusley_freitas.dto.request.UpdateProductDTO;
import br.com.indra.jusley_freitas.dto.response.HistoryProductResponseDTO;
import br.com.indra.jusley_freitas.dto.response.ProductResponseDTO;
import br.com.indra.jusley_freitas.service.PriceHistoryService;
import br.com.indra.jusley_freitas.service.ProductService;
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

    private final ProductService service;
    private final PriceHistoryService historyService;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO requestDTO){
        return new ResponseEntity<>(service.createProduct(requestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<Void>  updateProduct(@PathVariable UUID productId, @RequestBody UpdateProductDTO product){
        service.updateProduct(product, productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/update/price/{productId}")
    public ResponseEntity<Void>  updatePriceProduct(@PathVariable UUID productId, @RequestBody UpdatePriceProductDTO product){
        service.updatePriceProduct(product, productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("{productId}")
    public ResponseEntity<ProductResponseDTO> getProductsById(@PathVariable UUID productId) {
        return new ResponseEntity<>(service.findProductById(productId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return new ResponseEntity<>(service.findAllProducts(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void>  deleteProduct(@PathVariable UUID productId){
        service.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{productId}/price-history")
    public ResponseEntity<List<HistoryProductResponseDTO>> getPriceHistory(@PathVariable UUID productId){
        return new ResponseEntity<>(historyService.getHistoryByProductId(productId), HttpStatus.OK);
    }

}
