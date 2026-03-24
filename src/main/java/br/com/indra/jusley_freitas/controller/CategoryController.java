package br.com.indra.jusley_freitas.controller;

import br.com.indra.jusley_freitas.dto.request.category.CategoryRequestDTO;
import br.com.indra.jusley_freitas.dto.response.CategoryResponseDTO;
import br.com.indra.jusley_freitas.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
@Tag(name = "Categories", description = "Endpoints for category management")
public class CategoryController {

    private final CategoryService service;

    @PostMapping
    @Operation(summary = "Create a category", description = "Create a new category with the provided details.")
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryRequestDTO requestDTO){
        return new ResponseEntity<>(service.createCategory(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Search all categories.", description = "Returns all saved categories.")
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        return new ResponseEntity<>(service.findAllCategories(), HttpStatus.OK);
    }

}
