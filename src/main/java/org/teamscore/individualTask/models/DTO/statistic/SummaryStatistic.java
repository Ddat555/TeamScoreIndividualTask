package org.teamscore.individualTask.models.DTO.statistic;

import java.math.BigDecimal;

public class SummaryStatistic {
    private final BigDecimal totalSum;
    private final int countCosts;

    public SummaryStatistic(BigDecimal totalSum, int countCosts) {
        this.totalSum = totalSum;
        this.countCosts = countCosts;
    }

    public BigDecimal getTotalSum() {
        return totalSum;
    }

    public int getCountCosts() {
        return countCosts;
    }
}
