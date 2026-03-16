package org.teamscore.individualTask.models.DTO.statistic;

import java.math.BigDecimal;


public abstract class AbstractStatistic {
    private final String name;
    private final BigDecimal totalSum;

    public AbstractStatistic(String name, BigDecimal totalSum) {
        this.name = name;
        this.totalSum = totalSum;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getTotalSum() {
        return totalSum;
    }
}
