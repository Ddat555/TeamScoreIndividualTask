package org.teamscore.individualTask.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamscore.individualTask.models.DTO.entity.CategoryDTO;
import org.teamscore.individualTask.models.DTO.entity.createDTO.CreateCategoryDTO;
import org.teamscore.individualTask.models.entity.Category;
import org.teamscore.individualTask.services.CategoryService;

@Tag(name = "Category")
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Get all categories")
    @GetMapping
    public ResponseEntity<?> getAll(Pageable pageable) {
        var result = categoryService.getAllCategory(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "Search by name")
    @GetMapping("/search")
    public ResponseEntity<?> getByName(
            @Parameter(description = "Category name")
            @RequestParam(name = "name") String name) {
        var result = categoryService.getCategoryByName(name);
        if(result != null)
            return ResponseEntity.status(HttpStatus.OK).body(result);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Категория с таким именем не найдена");
    }

    @Operation(summary = "Create category")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateCategoryDTO categoryDTO) {
        System.out.println(categoryDTO);
        var category  = categoryService.createCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @Operation(summary = "Update category")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody CategoryDTO categoryDTO) {
        var category = categoryService.updateCategory(categoryDTO);
        if(category != null)
            return ResponseEntity.status(HttpStatus.OK).body(category);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Категория с таким ид не найдена");
    }

    @Operation(summary = "Delete category")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(
            @Parameter(description = "Category ID")
            @PathVariable(name = "id") Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}