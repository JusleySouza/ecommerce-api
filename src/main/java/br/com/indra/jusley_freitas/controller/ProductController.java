package br.com.indra.jusley_freitas.controller;

import br.com.indra.jusley_freitas.dto.request.product.ProductRequestDTO;
import br.com.indra.jusley_freitas.dto.request.product.UpdatePriceProductDTO;
import br.com.indra.jusley_freitas.dto.request.product.UpdateProductDTO;
import br.com.indra.jusley_freitas.dto.response.HistoryProductResponseDTO;
import br.com.indra.jusley_freitas.dto.response.ProductResponseDTO;
import br.com.indra.jusley_freitas.service.PriceHistoryService;
import br.com.indra.jusley_freitas.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Tag(name = "Products", description = "Endpoints for product management")
public class ProductController {

    private final ProductService service;
    private final PriceHistoryService historyService;

    @PostMapping
    @Operation(summary = "Create a product", description = "Create a new product with the provided details.")
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO requestDTO){
        return new ResponseEntity<>(service.createProduct(requestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update/{productId}")
    @Operation(summary = "Update a product", description = "Update a new product with the provided details.")
    public ResponseEntity<Void>  updateProduct(@PathVariable UUID productId, @Valid @RequestBody UpdateProductDTO product){
        service.updateProduct(product, productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/update/price/{productId}")
    @Operation(summary = "Update the product price", description = "Update the product price with the new value provided.")
    public ResponseEntity<Void>  updatePriceProduct(@PathVariable UUID productId, @Valid @RequestBody UpdatePriceProductDTO product){
        service.updatePriceProduct(product, productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("{productId}")
    @Operation(summary = "Search the product by ID.", description = "Returns the product data")
    public ResponseEntity<ProductResponseDTO> getProductsById(@PathVariable UUID productId) {
        return new ResponseEntity<>(service.findProductById(productId), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Search all products.", description = "Returns all saved products.")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return new ResponseEntity<>(service.findAllProducts(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{productId}")
    @Operation(summary = "Delete the product by ID.", description = "It logically excludes the product.")
    public ResponseEntity<Void>  deleteProduct(@PathVariable UUID productId){
        service.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/reactivate/{productId}")
    @Operation(summary = "Reactivate the product by ID.", description = "It logically activates the product.")
    public ResponseEntity<Void>  reactivateProduct(@PathVariable UUID productId){
        service.reactivateProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{productId}/price-history")
    @Operation(summary = "Search history by ID.", description = "Returns the price history of the product.")
    public ResponseEntity<List<HistoryProductResponseDTO>> getPriceHistory(@PathVariable UUID productId){
        return new ResponseEntity<>(historyService.getHistoryByProductId(productId), HttpStatus.OK);
    }

}
