package org.teamscore.individualTask;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.teamscore.individualTask.models.DTO.statistic.CategoryStatistic;
import org.teamscore.individualTask.models.DTO.statistic.DetailedStatisticInfo;
import org.teamscore.individualTask.models.DTO.statistic.SellerStatistic;
import org.teamscore.individualTask.models.DTO.statistic.SummaryStatistic;
import org.teamscore.individualTask.models.entity.Category;
import org.teamscore.individualTask.models.entity.Cost;
import org.teamscore.individualTask.models.entity.TypePayment;
import org.teamscore.individualTask.repositories.CategoryRepository;
import org.teamscore.individualTask.repositories.CostRepository;
import org.teamscore.individualTask.repositories.TypePaymentRepository;
import org.teamscore.individualTask.services.StatisticService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatisticServiceTest {

    @Mock
    private CostRepository costRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private TypePaymentRepository typePaymentRepository;

    @InjectMocks
    private StatisticService statisticService;

    private final LocalDateTime from = LocalDateTime.of(2026, 1, 1, 0, 0);
    private final LocalDateTime to = LocalDateTime.of(2026, 1, 31, 23, 59);


    @Test
    void getStatisticByPeriod_shouldReturnCorrectSummary() {
        List<Cost> mockCosts = Arrays.asList(
                createCost(BigDecimal.valueOf(100), "Test1"),
                createCost(BigDecimal.valueOf(200), "Test2")
        );

        when(costRepository.findAllByPeriod(from, to)).thenReturn(mockCosts);

        SummaryStatistic result = statisticService.getStatisticByPeriod(from, to);

        assertThat(result.getTotalSum()).isEqualByComparingTo("300");
        assertThat(result.getCountCosts()).isEqualTo(2);
    }

    @Test
    void getStatisticByPeriod_whenNoCosts_shouldReturnZeroSummary() {
        when(costRepository.findAllByPeriod(from, to)).thenReturn(Collections.emptyList());
        SummaryStatistic result = statisticService.getStatisticByPeriod(from, to);

        assertThat(result.getTotalSum()).isEqualByComparingTo("0");
        assertThat(result.getCountCosts()).isEqualTo(0);
    }

    @Test
    void getCategoryStatisticByPeriod_shouldCalculateCorrectPercentages() {
        Category cat1 = new Category("Еда", "red", "Описание");
        Category cat2 = new Category("Транспорт", "blue", "Описание");

        List<Object[]> mockResults = Arrays.asList(
                new Object[]{cat1, BigDecimal.valueOf(300)},
                new Object[]{cat2, BigDecimal.valueOf(200)}
        );

        List<Cost> mockCosts = Arrays.asList(
                createCost(BigDecimal.valueOf(300), "Test1"),
                createCost(BigDecimal.valueOf(200), "Test2")
        );

        when(costRepository.getCategorySums(from, to)).thenReturn(mockResults);
        when(costRepository.findAllByPeriod(from, to)).thenReturn(mockCosts);

        DetailedStatisticInfo<CategoryStatistic> result =
                statisticService.getCategoryStatisticByPeriod(from, to);

        List<CategoryStatistic> stats = result.getStatisticList();

        assertThat(stats).hasSize(2);

        assertThat(stats.get(0).getName()).isEqualTo("Еда");
        assertThat(stats.get(0).getPercentage()).isEqualTo(60.0);
        assertThat(stats.get(0).getColor()).isEqualTo("red");

        assertThat(stats.get(1).getName()).isEqualTo("Транспорт");
        assertThat(stats.get(1).getPercentage()).isEqualTo(40.0);
    }

    @Test
    void getCategoryStatisticByPeriod_whenTotalSumZero_shouldReturnEmptyList() {
        List<Object[]> mockResults = List.<Object[]>of(
                new Object[]{new Category("Еда", "red", ""), BigDecimal.ZERO}
        );

        when(costRepository.getCategorySums(from, to)).thenReturn(mockResults);
        when(costRepository.findAllByPeriod(from, to)).thenReturn(Collections.emptyList());

        DetailedStatisticInfo<CategoryStatistic> result =
                statisticService.getCategoryStatisticByPeriod(from, to);

        assertThat(result.getStatisticList()).isEmpty();
    }

    @Test
    void getCategoryStatisticByPeriod_withDivisionPrecision_shouldRoundCorrectly() {
        Category cat1 = new Category("Еда", "red", "");

        List<Object[]> mockResults = List.<Object[]>of(
                new Object[]{cat1, new BigDecimal("33.33")}
        );

        List<Cost> mockCosts = List.of(
                createCost(new BigDecimal("100.00"), "Test")
        );

        when(costRepository.getCategorySums(from, to)).thenReturn(mockResults);
        when(costRepository.findAllByPeriod(from, to)).thenReturn(mockCosts);

        DetailedStatisticInfo<CategoryStatistic> result =
                statisticService.getCategoryStatisticByPeriod(from, to);

        CategoryStatistic stat = result.getStatisticList().get(0);
        assertThat(stat.getPercentage()).isEqualTo(33.33);
    }

    @Test
    void getCategoryStatisticByPeriod_withRoundingHalfUp_shouldRoundCorrectly() {
        Category cat1 = new Category("Тест", "color", "");

        List<Object[]> mockResults = List.<Object[]>of(
                new Object[]{cat1, new BigDecimal("33.335")}
        );

        List<Cost> mockCosts = List.of(
                createCost(new BigDecimal("100.00"), "Test")
        );

        when(costRepository.getCategorySums(from, to)).thenReturn(mockResults);
        when(costRepository.findAllByPeriod(from, to)).thenReturn(mockCosts);

        DetailedStatisticInfo<CategoryStatistic> result =
                statisticService.getCategoryStatisticByPeriod(from, to);

        assertThat(result.getStatisticList().get(0).getPercentage()).isEqualTo(33.34);
    }

    @Test
    void getSellerStatisticByPeriod_shouldSortBySumDescending() {
        List<Object[]> mockResults = Arrays.asList(
                new Object[]{"Продавец A", new BigDecimal("500")},
                new Object[]{"Продавец B", new BigDecimal("300")},
                new Object[]{"Продавец C", new BigDecimal("700")}
        );

        List<Cost> mockCosts = Arrays.asList(
                createCost(new BigDecimal("500"), "A"),
                createCost(new BigDecimal("300"), "B"),
                createCost(new BigDecimal("700"), "C")
        );

        when(costRepository.getSellersSums(from, to)).thenReturn(mockResults);
        when(costRepository.findAllByPeriod(from, to)).thenReturn(mockCosts);

        DetailedStatisticInfo<SellerStatistic> result =
                statisticService.getSellerStatisticByPeriod(from, to);

        List<SellerStatistic> stats = result.getStatisticList();

        assertThat(stats).hasSize(3);
        assertThat(stats.get(0).getName()).isEqualTo("Продавец C");
        assertThat(stats.get(0).getTotalSum()).isEqualByComparingTo("700");

        assertThat(stats.get(1).getName()).isEqualTo("Продавец A");
        assertThat(stats.get(2).getName()).isEqualTo("Продавец B");
    }

    @Test
    void getTypePaymentStatisticByPeriod_withNullTotalSum_shouldHandleGracefully() {
        TypePayment typePayment = new TypePayment();
        typePayment.setId(1L);
        typePayment.setName("Карта");

        List<Object[]> mockResults = List.<Object[]>of(
                new Object[]{typePayment, new BigDecimal("100")}
        );

        when(costRepository.getTypePaymentSums(from, to)).thenReturn(mockResults);
        when(costRepository.findAllByPeriod(from, to)).thenReturn(null);

        assertThatThrownBy(() -> statisticService.getTypePaymentStatisticByPeriod(from, to))
                .isInstanceOf(NullPointerException.class);
    }

    private Cost createCost(BigDecimal sum, String seller) {
        Cost cost = new Cost();
        cost.setSum(sum);
        cost.setSellerName(seller);
        return cost;
    }
}
