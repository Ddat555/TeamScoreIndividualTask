package org.teamscore.individualTask.models.DTO.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.teamscore.individualTask.models.entity.TypePayment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CostDTO {
    private Long id;
    private LocalDateTime dateTimePay;
    private String sellerName;
    private BigDecimal sum;
    private TypePaymentDTO typePayment;
    private List<CategoryDTO> categories;
}
