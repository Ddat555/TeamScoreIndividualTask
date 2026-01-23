package org.teamscore.individualTask.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.teamscore.individualTask.models.DTO.entity.CostDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Cost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private LocalDateTime dateTimePay;
    private String sellerName;
    private BigDecimal sum;
    @ManyToOne
    private TypePayment typePayment;
    @ManyToMany
    @JoinTable(
            name = "category_cost",
            joinColumns = @JoinColumn(name = "cost_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    public Cost(String sellerName, BigDecimal sum, TypePayment typePayment, Set<Category> categories) {
        this.sellerName = sellerName;
        this.sum = sum;
        this.typePayment = typePayment;
        this.categories = categories;
    }

    public CostDTO toDTO() {
        CostDTO costDTO = new CostDTO();
        costDTO.setId(this.id);
        costDTO.setSum(this.sum);
        costDTO.setSellerName(this.sellerName);
        costDTO.setDateTimePay(this.dateTimePay);
        costDTO.setTypePayment(this.typePayment.toDTO());
        costDTO.setCategories(this.categories.stream().map(Category::toDTO).collect(Collectors.toSet()));
        return costDTO;
    }
}
