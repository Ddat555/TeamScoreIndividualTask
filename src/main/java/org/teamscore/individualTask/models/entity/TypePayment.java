package org.teamscore.individualTask.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.teamscore.individualTask.models.DTO.entity.TypePaymentDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TypePayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    @OneToMany(mappedBy = "typePayment")
    @JsonIgnore
    private Set<Cost> costs = new HashSet<>();

    public TypePayment(String name) {
        this.name = name;
    }

    public TypePayment(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean isUseInCosts(){
        return !costs.isEmpty();
    }

    public TypePaymentDTO toDTO(){
        TypePaymentDTO typePaymentDTO = new TypePaymentDTO();
        typePaymentDTO.setId(this.id);
        typePaymentDTO.setName(this.name);
        return typePaymentDTO;
    }
}
