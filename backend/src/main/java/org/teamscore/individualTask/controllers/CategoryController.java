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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.teamscore.individualTask.exceptions.CannotDeleteEntityException;
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
        return "categories/list";
    }

    @Operation(summary = "Get category by ID")
    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, Model model) {
        var category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "categories/view";
    }

    @Operation(summary = "Search by name")
    @GetMapping("/search")
    public String getByName(
            @RequestParam(name = "name") String name,
            Model model) {
        var result = categoryService.getCategoryByName(name);

        if (result != null) {
            model.addAttribute("category", result);
            return "categories/view";
        } else {
            model.addAttribute("error", "Категория с именем '" + name + "' не найдена");
            return "categories/search";
        }
    }

    @Operation(summary = "Show create form")
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new CreateCategoryDTO());
        return "categories/create";
    }

    @Operation(summary = "Create category")
    @PostMapping
    public String create(
            @Valid @ModelAttribute("category") CreateCategoryDTO categoryDTO,
            BindingResult result) {

        if (result.hasErrors()) {
            return "categories/create";
        }

        categoryService.createCategory(categoryDTO);
        return "redirect:/categories";
    }

    @Operation(summary = "Show edit form")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        var category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "categories/edit";
    }

    @Operation(summary = "Update category")
    @PutMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute("category") CategoryDTO categoryDTO,
            BindingResult result) {

        if (result.hasErrors()) {
            return "categories/edit";
        }

        categoryService.updateCategory(categoryDTO);
        return "redirect:/categories";
    }

    @Operation(summary = "Delete category")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.deleteCategory(id);
            redirectAttributes.addFlashAttribute("success", "Категория удалена");
        } catch (CannotDeleteEntityException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/categories";
    }
}