package org.teamscore.individualTask.models.DTO.statistic;

import java.math.BigDecimal;

public class TypePaymentStatistic extends AbstractStatistic {
    private final double percentage;

    public TypePaymentStatistic(String name, BigDecimal totalSum, double percentage) {
        super(name, totalSum);
        this.percentage = percentage;
    }

    public double getPercentage() {
        return percentage;
    }
}
