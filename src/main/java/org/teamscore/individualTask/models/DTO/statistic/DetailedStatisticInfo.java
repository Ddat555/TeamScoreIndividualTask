package org.teamscore.individualTask.models.DTO.statistic;

import java.util.List;

public class DetailedStatisticInfo<T extends AbstractStatistic> {
    private final SummaryStatistic summaryStatistic;
    private final List<T> statisticList;

    public DetailedStatisticInfo(SummaryStatistic summaryStatistic, List<T> statisticList) {
        this.summaryStatistic = summaryStatistic;
        this.statisticList = statisticList;
    }

    public SummaryStatistic getSummaryStatistic() {
        return summaryStatistic;
    }

    public List<T> getStatisticList() {
        return statisticList;
    }
}
