package org.teamscore.individualTask.models.DTO.entity;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.teamscore.individualTask.models.entity.Cost;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    @NotNull
    private TypePaymentDTO typePayment;
    @NotEmpty
    @Size(min = 1, max = 10, message = "Должна быть от 1 до 10 категорий")
    private Set<CategoryDTO> categories;

    public CostDTO(Cost cost){
        this.id = cost.getId();
        this.dateTimePay = cost.getDateTimePay();
        this.sellerName = cost.getSellerName();
        this.sum = cost.getSum();
        this.typePayment = new TypePaymentDTO(cost.getTypePayment());
        this.categories = cost.getCategories().stream().map(CategoryDTO::new).collect(Collectors.toSet());
    }
}
