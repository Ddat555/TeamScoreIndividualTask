package org.teamscore.individualTask.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teamscore.individualTask.models.DTO.statistic.StatisticRequest;
import org.teamscore.individualTask.services.StatisticService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Tag(name = "Statistic")
@RestController
@RequestMapping("/api/v1/statistic")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @Operation(summary = "Get summary statistic")
    @GetMapping("/summary")
    public ResponseEntity<?> getSummaryStatistic(
            @Parameter @RequestParam(name = "from") LocalDateTime from,
            @Parameter @RequestParam("to") LocalDateTime to){
        var result = statisticService.getStatisticByPeriod(from,to);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "Get statistic with categories")
    @GetMapping("/category")
    public ResponseEntity<?> getCategoryStatistic(@RequestParam(name = "from")LocalDateTime from, @RequestParam("to") LocalDateTime to){
        var result = statisticService.getCategoryStatisticByPeriod(from, to);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "Get statistic with type payment")
    @GetMapping("/type-payment")
    public ResponseEntity<?> getTypePaymentStatistic(@RequestParam(name = "from") LocalDateTime from, @RequestParam("to") LocalDateTime to){
        var result = statisticService.getTypePaymentStatisticByPeriod(from, to);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(summary = "Get statistic with top sellers")
    @GetMapping("/top-seller")
    public ResponseEntity<?> getTopSellers(@RequestParam(name = "from") LocalDateTime from, @RequestParam("to") LocalDateTime to){
        var result = statisticService.getSellerStatisticByPeriod(from, to);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
