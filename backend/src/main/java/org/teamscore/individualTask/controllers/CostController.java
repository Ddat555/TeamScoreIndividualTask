package org.teamscore.individualTask.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.teamscore.individualTask.models.DTO.entity.CostDTO;
import org.teamscore.individualTask.models.DTO.entity.createDTO.CreateCostDTO;
import org.teamscore.individualTask.services.CostService;

@Tag(name = "Cost")
@Controller
@RequestMapping("/costs")
public class CostController {

    @Autowired
    private CostService costService;

    @Operation(summary = "Get all costs")
    @GetMapping
    public String getAll(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            Model model) {
        var result = costService.getAllCost(pageable);
        model.addAttribute("costs", result);
        return "costs/list";
    }

    @Operation(summary = "Search by ID")
    @GetMapping("/search")
    public String getById(
            @RequestParam(name = "id") Long id,
            Model model) {
        var result = costService.getCostById(id);

        if (result != null) {
            model.addAttribute("cost", result);
            return "costs/view";
        } else {
            model.addAttribute("error", "Расход с ID " + id + " не найден");
            return "costs/search";
        }
    }

    @Operation(summary = "Show create form")
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("cost", new CreateCostDTO());
        return "costs/create";
    }

    @Operation(summary = "Create cost")
    @PostMapping
    public String create(
            @Valid @ModelAttribute("cost") CreateCostDTO costDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "costs/create";
        }

        try {
            var cost = costService.createCost(costDTO);
            redirectAttributes.addFlashAttribute("success", "Расход успешно создан с ID: " + cost.getId());
            return "redirect:/costs";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при создании расхода: " + e.getMessage());
            return "redirect:/costs/new";
        }
    }

    @Operation(summary = "Show edit form")
    @GetMapping("/edit/{id}")
    public String showEditForm(
            @Parameter(description = "Cost ID")
            @PathVariable Long id,
            Model model,
            RedirectAttributes redirectAttributes) {

        var cost = costService.getCostById(id);
        if (cost != null) {
            model.addAttribute("cost", cost);
            return "costs/edit";
        } else {
            redirectAttributes.addFlashAttribute("error", "Расход с ID " + id + " не найден");
            return "redirect:/costs";
        }
    }

    @Operation(summary = "Update cost")
    @PutMapping("/{id}")
    public String update(
            @Parameter(description = "Cost ID")
            @PathVariable Long id,
            @Valid @ModelAttribute("cost") CostDTO costDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "costs/edit";
        }

        try {
            costDTO.setId(id);
            var updated = costService.updateCost(costDTO);

            if (updated != null) {
                redirectAttributes.addFlashAttribute("success", "Расход успешно обновлен");
            } else {
                redirectAttributes.addFlashAttribute("error", "Расход с ID " + id + " не найден");
                return "redirect:/costs";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при обновлении: " + e.getMessage());
        }

        return "redirect:/costs";
    }

    @Operation(summary = "Delete cost")
    @DeleteMapping("/{id}")
    public String delete(
            @Parameter(description = "Cost ID")
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        try {
            costService.deleteCost(id);
            redirectAttributes.addFlashAttribute("success", "Расход с ID " + id + " успешно удален");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении: " + e.getMessage());
        }

        return "redirect:/costs";
    }
}