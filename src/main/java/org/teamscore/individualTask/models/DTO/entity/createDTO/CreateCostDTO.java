package org.teamscore.individualTask.models.DTO.entity.createDTO;

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
    private String sellerName;
    private BigDecimal sum;
    private TypePaymentDTO typePayment;
    private List<CategoryDTO> categories;
}
