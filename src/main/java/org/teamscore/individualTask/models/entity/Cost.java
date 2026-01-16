package org.teamscore.individualTask.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private List<Category> categories = new ArrayList<>();

    public Cost( String sellerName, BigDecimal sum, TypePayment typePayment, List<Category> categories) {
        this.sellerName = sellerName;
        this.sum = sum;
        this.typePayment = typePayment;
        this.categories = categories;
    }
}
