package org.teamscore.individualTask;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.teamscore.individualTask.models.DTO.entity.CategoryDTO;
import org.teamscore.individualTask.models.DTO.entity.CostDTO;
import org.teamscore.individualTask.models.DTO.entity.TypePaymentDTO;
import org.teamscore.individualTask.models.entity.Category;
import org.teamscore.individualTask.models.entity.Cost;
import org.teamscore.individualTask.models.entity.TypePayment;
import org.teamscore.individualTask.services.ConverterDTOService;
import org.teamscore.individualTask.services.StatisticService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ConverterDTOServiceTest {

    @InjectMocks
    private ConverterDTOService converterDTOService;

    @Test
    void categoryConvert() {
        Category category = new Category(
                1L,
                "Категория 1",
                "red",
                "Описание 1"
        );

        CategoryDTO categoryDTO = converterDTOService.categoryConvert(category);

        assertNotNull(categoryDTO);
        assertEquals(1L, categoryDTO.getId());
        assertEquals("Категория 1", categoryDTO.getName());
        assertEquals("red", categoryDTO.getColor());
        assertEquals("Описание 1", categoryDTO.getDescription());
    }

    @Test
    void typePaymentConvert() {
        TypePayment typePayment = new TypePayment(
                1L,
                "Карта"
        );

        TypePaymentDTO typePaymentDTO = converterDTOService.typePaymentConvert(typePayment);

        assertNotNull(typePaymentDTO);
        assertEquals(1L, typePaymentDTO.getId());
        assertEquals("Карта", typePaymentDTO.getName());
    }

    @Test
    void costConvert() {
        Cost cost = new Cost(1L,
                LocalDateTime.of(2026,1,1, 0,0,0),
                "Продавец 1",
                BigDecimal.ONE,
                new TypePayment(1L,"name"),
                Arrays.asList(new Category("Категория 1", "red", "desc"), new Category("Категория 2", "blue", "desc")));

        CostDTO costDTO = converterDTOService.costConvert(cost);

        assertNotNull(costDTO);
        assertEquals(1L, costDTO.getId());
        assertEquals(LocalDateTime.of(2026,1,1,0,0,0), costDTO.getDateTimePay());
        assertEquals("Продавец 1", costDTO.getSellerName());
        assertEquals(BigDecimal.ONE, costDTO.getSum());
    }

}