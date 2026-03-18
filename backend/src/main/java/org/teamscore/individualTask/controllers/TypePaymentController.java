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
import org.teamscore.individualTask.models.DTO.entity.TypePaymentDTO;
import org.teamscore.individualTask.models.DTO.entity.createDTO.CreateTypePaymentDTO;
import org.teamscore.individualTask.services.TypePaymentService;

@Tag(name = "Type Payment")
@Controller
@RequestMapping("/type-payments")
public class TypePaymentController {

    @Autowired
    private TypePaymentService typePaymentService;

    @Operation(summary = "Get all type payments")
    @GetMapping
    public String getAll(
            @PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable,
            Model model) {
        var typePayments = typePaymentService.getAllTypePayment(pageable);
        model.addAttribute("typePayments", typePayments);
        return "type-payments/list";
    }

    @Operation(summary = "Get typePayment by ID")
    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, Model model) {
        var result = typePaymentService.getTypePaymentById(id);
        if (result != null) {
            model.addAttribute("typePayment", result);
            return "type-payments/view";
        } else {
            return "redirect:/type-payments";
        }
    }

    @Operation(summary = "Search by name")
    @GetMapping("/search")
    public String getByName(
            @RequestParam(name = "name") String name,
            Model model) {
        var result = typePaymentService.getTypePaymentByName(name);

        if (result != null) {
            model.addAttribute("typePayment", result);
            return "type-payments/view";
        } else {
            model.addAttribute("error", "Тип оплаты с названием '" + name + "' не найден");
            return "type-payments/search";
        }
    }

    @Operation(summary = "Show create form")
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("typePayment", new CreateTypePaymentDTO());
        return "type-payments/create";
    }

    @Operation(summary = "Create type payment")
    @PostMapping
    public String create(
            @Valid @ModelAttribute("typePayment") CreateTypePaymentDTO typePaymentDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "type-payments/create";
        }

        try {
            var existing = typePaymentService.getTypePaymentByName(typePaymentDTO.getName());
            if (existing != null) {
                result.rejectValue("name", "error.typePayment", "Тип оплаты с таким названием уже существует");
                return "type-payments/create";
            }

            var typePayment = typePaymentService.createTypePayment(typePaymentDTO);
            redirectAttributes.addFlashAttribute("success",
                    "Тип оплаты '" + typePayment.getName() + "' успешно создан");
            return "redirect:/type-payments";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при создании: " + e.getMessage());
            return "redirect:/type-payments/new";
        }
    }

    @Operation(summary = "Show edit form")
    @GetMapping("/edit/{id}")
    public String showEditForm(
            @Parameter(description = "Type payment ID")
            @PathVariable Long id,
            Model model,
            RedirectAttributes redirectAttributes) {

        var typePayment = typePaymentService.getTypePaymentById(id);
        if (typePayment != null) {
            model.addAttribute("typePayment", typePayment);
            return "type-payments/edit";
        } else {
            redirectAttributes.addFlashAttribute("error", "Тип оплаты с ID " + id + " не найден");
            return "redirect:/type-payments";
        }
    }

    @Operation(summary = "Update type payment")
    @PutMapping("/{id}")
    public String update(
            @Parameter(description = "Type payment ID")
            @PathVariable Long id,
            @Valid @ModelAttribute("typePayment") TypePaymentDTO typePaymentDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "type-payments/edit";
        }

        try {
            typePaymentDTO.setId(id);

            var existing = typePaymentService.getTypePaymentByName(typePaymentDTO.getName());
            if (existing != null && !existing.getId().equals(id)) {
                result.rejectValue("name", "error.typePayment", "Тип оплаты с таким названием уже существует");
                return "type-payments/edit";
            }

            var updated = typePaymentService.updateTypePayment(typePaymentDTO);

            if (updated != null) {
                redirectAttributes.addFlashAttribute("success",
                        "Тип оплаты '" + updated.getName() + "' успешно обновлен");
            } else {
                redirectAttributes.addFlashAttribute("error", "Тип оплаты с ID " + id + " не найден");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при обновлении: " + e.getMessage());
        }

        return "redirect:/type-payments";
    }

    @Operation(summary = "Delete type payment")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            typePaymentService.deleteTypePayment(id);
            redirectAttributes.addFlashAttribute("success", "Тип оплаты удален");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Нельзя удалить тип оплаты, так как он используется в расходах");
        }
        return "redirect:/type-payments";
    }
}