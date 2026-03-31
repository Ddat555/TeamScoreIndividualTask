package org.teamscore.individualTask.models.DTO.statistic;

import java.math.BigDecimal;

public class CategoryStatistic extends AbstractStatistic {
    private final double percentage;
    private final String color;


    public CategoryStatistic(String name, BigDecimal totalSum, double percentage, String color) {
        super(name, totalSum);
        this.percentage = percentage;
        this.color = color;
    }

    public double getPercentage() {
        return percentage;
    }

    public String getColor() {
        return color;
    }
}
