package org.teamscore.individualTask.models.DTO.entity;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.teamscore.individualTask.models.entity.Category;
import org.teamscore.individualTask.models.entity.Cost;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CostDTO {
    @NotNull
    private Long id;
    @Null
    private LocalDateTime dateTimePay;
    @NotBlank
    @Size(min = 2, max = 50, message = "Название должно быть от 2 до 50 символов")
    private String sellerName;
    @NotNull
    @Positive
    @Digits(integer = 10, fraction = 2, message = "Формат суммы: 10 цифр до точки и 2 после")
    private BigDecimal sum;
    private TypePaymentDTO typePayment;
    private Set<CategoryDTO> categories;

    private Long typePaymentId;
    private List<Long> categoryIds;

    public CostDTO(Cost cost) {
        this.id = cost.getId();
        this.sellerName = cost.getSellerName();
        this.sum = cost.getSum();
        this.dateTimePay = cost.getDateTimePay();
        this.typePayment = new TypePaymentDTO(cost.getTypePayment());
        this.typePaymentId = cost.getTypePayment().getId();
        this.categories = cost.getCategories().stream().map(CategoryDTO::new).collect(Collectors.toSet());
        this.categoryIds = cost.getCategories().stream().map(Category::getId).collect(Collectors.toList());
    }
}
