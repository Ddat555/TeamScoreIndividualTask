package org.teamscore.individualTask.models.DTO.statistic;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StatisticRequest {
    private LocalDate dateFrom;
    private LocalDate dateTo;
}
