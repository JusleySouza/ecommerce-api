package br.com.indra.jusley_freitas.controller;

import br.com.indra.jusley_freitas.dto.request.sub_category.SubCategoryRequestDTO;
import br.com.indra.jusley_freitas.dto.response.SubCategoryResponseDTO;
import br.com.indra.jusley_freitas.service.SubCategoryService;
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
@RequestMapping("/categories/{categoryId}/sub_categories")
@Tag(name = "SubCategories", description = "Endpoints for sub_category management")
public class SubCategoryController {

    private final SubCategoryService service;

    @PostMapping
    @Operation(summary = "Create a sub_category", description = "Create a new sub_category with the provided details.")
    public ResponseEntity<SubCategoryResponseDTO> createSubCategory(@PathVariable UUID categoryId, @Valid @RequestBody SubCategoryRequestDTO requestDTO){
        return new ResponseEntity<>(service.createSubCategory(categoryId, requestDTO), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "List subcategories by category.", description = "Returns all subcategories for a given category ID.")
    public ResponseEntity<List<SubCategoryResponseDTO>> findAllByCategoriesId(@PathVariable UUID categoryId) {
        return new ResponseEntity<>(service.findAllSubCategoriesByCategoryId(categoryId), HttpStatus.OK);
    }

}
