package org.teamscore.individualTask.models.DTO.entity.createDTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.teamscore.individualTask.models.DTO.entity.CategoryDTO;
import org.teamscore.individualTask.models.DTO.entity.TypePaymentDTO;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCostDTO {
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
    private List<CategoryDTO> categories;
}
