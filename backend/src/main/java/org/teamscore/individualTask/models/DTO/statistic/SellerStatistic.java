package org.teamscore.individualTask.models.DTO.statistic;

import java.math.BigDecimal;

public class SellerStatistic extends AbstractStatistic {
    public SellerStatistic(String name, BigDecimal totalSum) {
        super(name, totalSum);
    }
}
