package org.teamscore.individualTask.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamscore.individualTask.models.DTO.entity.TypePaymentDTO;
import org.teamscore.individualTask.models.DTO.entity.createDTO.CreateTypePaymentDTO;
import org.teamscore.individualTask.models.entity.TypePayment;
import org.teamscore.individualTask.services.TypePaymentService;

@Tag(name = "Type Payment")
@RestController
@RequestMapping("/api/v1/type-payment")
public class TypePaymentController {

    @Autowired
    private TypePaymentService typePaymentService;

    @Operation(summary = "Get all type payments")
    @GetMapping
    public ResponseEntity<?> getAll(Pageable pageable) {
        var typePayments = typePaymentService.getAllTypePayment(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(typePayments);
    }

    @Operation(summary = "Search by name")
    @GetMapping("/search")
    public ResponseEntity<?> getByName(
            @Parameter(description = "Type payment name")
            @RequestParam(name = "name") String name) {
        var result = typePaymentService.getTypePaymentByName(name);
        if(result != null)
            return ResponseEntity.status(HttpStatus.OK).body(result);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Тип оплаты с таким названием не найден");
    }

    @Operation(summary = "Create type payment")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateTypePaymentDTO typePaymentDTO) {
        var typePayment = typePaymentService.createTypePayment(typePaymentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(typePayment);
    }

    @Operation(summary = "Update type payment")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody TypePaymentDTO typePaymentDTO) {
        var typePayment = typePaymentService.updateTypePayment(typePaymentDTO);
        if(typePayment != null)
            return ResponseEntity.status(HttpStatus.OK).body(typePayment);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Тип оплаты с таким ид не найден");
    }

    @Operation(summary = "Delete type payment")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(
            @Parameter(description = "Type payment ID")
            @PathVariable(name = "id") Long id) {
        typePaymentService.deleteTypePayment(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }
}
