package org.teamscore.individualTask.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamscore.individualTask.models.DTO.entity.CostDTO;
import org.teamscore.individualTask.models.DTO.entity.createDTO.CreateCostDTO;
import org.teamscore.individualTask.models.entity.Cost;
import org.teamscore.individualTask.services.CostService;

@Tag(name = "Cost")
@RestController
@RequestMapping("/api/v1/cost")
public class CostController {
    @Autowired
    private CostService costService;

    @Operation(summary = "Get all costs")
    @GetMapping
    public ResponseEntity<?> getAll(Pageable pageable) {
        var result = costService.getAllCost(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "Search by ID")
    @GetMapping("search")
    public ResponseEntity<?> getById(
            @Parameter(description = "Cost ID")
            @RequestParam(name = "id") Long id) {
        var result = costService.getCostById(id);
        if(result != null)
            return ResponseEntity.status(HttpStatus.OK).body(result);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Расход с таким ид не найден");
    }

    @Operation(summary = "Create cost")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateCostDTO costDTO) {
        var cost = costService.createCost(costDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(cost);
    }

    @Operation(summary = "Update cost")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody CostDTO costDTO) {
        var cost = costService.updateCost(costDTO);
        if(cost != null)
            return ResponseEntity.status(HttpStatus.OK).body(cost);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Расход с таким ид не найден");
    }

    @Operation(summary = "Delete cost")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(
            @Parameter(description = "Cost ID")
            @PathVariable(name = "id") Long id) {
        costService.deleteCost(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
