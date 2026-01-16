package org.teamscore.individualTask.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teamscore.individualTask.models.DTO.statistic.*;
import org.teamscore.individualTask.models.entity.Category;
import org.teamscore.individualTask.models.entity.TypePayment;
import org.teamscore.individualTask.repositories.CategoryRepository;
import org.teamscore.individualTask.repositories.CostRepository;
import org.teamscore.individualTask.repositories.TypePaymentRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class StatisticService {
    @Autowired
    private CostRepository costRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TypePaymentRepository typePaymentRepository;

    public SummaryStatistic getStatisticByPeriod(LocalDateTime from, LocalDateTime to) {
        var costList = costRepository.findAllByPeriod(from, to);
        BigDecimal totalSum = BigDecimal.ZERO;
        for (var cost : costList) {
            totalSum = totalSum.add(cost.getSum());
        }
        return new SummaryStatistic(totalSum, costList.size());
    }

    public DetailedStatisticInfo<CategoryStatistic> getCategoryStatisticByPeriod(LocalDateTime from, LocalDateTime to) {
        List<Object[]> results = costRepository.getCategorySums(from, to);
        Map<Category, BigDecimal> categoryMap = results.stream()
                .collect(Collectors.toMap(
                        cat -> (Category) cat[0],
                        per -> (BigDecimal) per[1]
                ));

        var summaryStatistic = getStatisticByPeriod(from, to);
        BigDecimal totalSum = summaryStatistic.getTotalSum();

        if (totalSum == null || totalSum.compareTo(BigDecimal.ZERO) == 0) {
            return new DetailedStatisticInfo<>(summaryStatistic, Collections.emptyList());
        }

        List<CategoryStatistic> categoryStatistics = new ArrayList<>();

        for (Map.Entry<Category, BigDecimal> entry : categoryMap.entrySet()) {
            Category category = entry.getKey();
            BigDecimal sum = entry.getValue();

            double percentage = sum.multiply(BigDecimal.valueOf(100))
                    .divide(totalSum, 2, RoundingMode.HALF_UP)
                    .doubleValue();

            CategoryStatistic categoryStatistic = new CategoryStatistic(
                    category.getName(),
                    sum,
                    percentage,
                    category.getColor());

            categoryStatistics.add(categoryStatistic);
        }

        categoryStatistics.sort((c1, c2) -> Double.compare(c2.getPercentage(), c1.getPercentage()));

        return new DetailedStatisticInfo<>(summaryStatistic, categoryStatistics);
    }

    public DetailedStatisticInfo<TypePaymentStatistic> getTypePaymentStatisticByPeriod(LocalDateTime from, LocalDateTime to) {
        List<Object[]> results = costRepository.getTypePaymentSums(from, to);
        Map<TypePayment, BigDecimal> typePaymentMap = results.stream()
                .collect(Collectors.toMap(
                        type -> (TypePayment) type[0],
                        per -> (BigDecimal) per[1]
                ));

        var summaryStatistic = getStatisticByPeriod(from, to);
        BigDecimal totalSum = summaryStatistic.getTotalSum();

        if (totalSum == null || totalSum.compareTo(BigDecimal.ZERO) == 0) {
            return new DetailedStatisticInfo<>(summaryStatistic, Collections.emptyList());
        }

        List<TypePaymentStatistic> typePaymentStatistics = new ArrayList<>();

        for (Map.Entry<TypePayment, BigDecimal> entry : typePaymentMap.entrySet()) {
            TypePayment typePayment = entry.getKey();
            BigDecimal sum = entry.getValue();

            double percentage = sum.multiply(BigDecimal.valueOf(100))
                    .divide(totalSum, 2, RoundingMode.HALF_UP)
                    .doubleValue();

            TypePaymentStatistic typePaymentStatistic = new TypePaymentStatistic(
                    typePayment.getName(),
                    sum,
                    percentage
            );
            typePaymentStatistics.add(typePaymentStatistic);
        }

        typePaymentStatistics.sort((c1, c2) -> Double.compare(c2.getPercentage(), c1.getPercentage()));

        return new DetailedStatisticInfo<>(summaryStatistic, typePaymentStatistics);
    }

    public DetailedStatisticInfo<SellerStatistic> getSellerStatisticByPeriod(LocalDateTime from, LocalDateTime to){
        List<Object[]> result = costRepository.getSellersSums(from, to);
        Map<String, BigDecimal> sellerMap = result.stream()
                .collect(Collectors.toMap(
                        name -> (String) name[0],
                        per -> (BigDecimal) per[1]
                ));

        var summaryStatistic = getStatisticByPeriod(from, to);
        BigDecimal totalSum = summaryStatistic.getTotalSum();

        if (totalSum == null || totalSum.compareTo(BigDecimal.ZERO) == 0) {
            return new DetailedStatisticInfo<>(summaryStatistic, Collections.emptyList());
        }

        List<SellerStatistic> sellerStatistics = new ArrayList<>();

        for (Map.Entry<String, BigDecimal> entry : sellerMap.entrySet()) {
            String seller = entry.getKey();
            BigDecimal sum = entry.getValue();

            SellerStatistic statistic = new SellerStatistic(seller, sum);
            sellerStatistics.add(statistic);
        }

        sellerStatistics.sort((c1, c2) -> c2.getTotalSum().compareTo(c1.getTotalSum()));

        return new DetailedStatisticInfo<>(summaryStatistic, sellerStatistics);
    }
}
