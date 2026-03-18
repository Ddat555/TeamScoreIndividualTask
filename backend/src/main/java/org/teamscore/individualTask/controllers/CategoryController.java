package org.teamscore.individualTask.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.teamscore.individualTask.models.DTO.entity.CategoryDTO;
import org.teamscore.individualTask.models.DTO.entity.createDTO.CreateCategoryDTO;
import org.teamscore.individualTask.services.CategoryService;

@Tag(name = "Category")
@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Get all categories")
    @GetMapping
    public String getAll(Pageable pageable, Model model) {
        var result = categoryService.getAllCategory(pageable);
        model.addAttribute("categories", result);
        return "categories";
    }

    @Operation(summary = "Search by name")
    @GetMapping("/search")
    public String getByName(
            @RequestParam(name = "name") String name,
            Model model) {
        var result = categoryService.getCategoryByName(name);

        if (result != null) {
            model.addAttribute("category", result);
        } else {
            model.addAttribute("error", "Категория с именем '" + name + "' не найдена");
        }
        return "searchCategory";
    }

    @Operation(summary = "Show create form")
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new CreateCategoryDTO());
        return "createCategory";
    }

    @Operation(summary = "Create category")
    @PostMapping
    public String create(
            @Valid @ModelAttribute("category") CreateCategoryDTO categoryDTO,
            BindingResult result) {

        if (result.hasErrors()) {
            return "createCategory";
        }

        categoryService.createCategory(categoryDTO);
        return "redirect:/categories";
    }

    @Operation(summary = "Show edit form")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        var category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "editCategory";
    }

    @Operation(summary = "Update category")
    @PutMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute CategoryDTO categoryDTO,
            BindingResult result) {

        if (result.hasErrors()) {
            return "editCategory";
        }

        categoryDTO.setId(id);
        categoryService.updateCategory(categoryDTO);
        return "redirect:/categories";
    }

    @Operation(summary = "Delete category")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories";
    }
}