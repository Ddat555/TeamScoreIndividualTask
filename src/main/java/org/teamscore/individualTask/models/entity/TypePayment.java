package org.teamscore.individualTask.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    private List<Cost> costs = new ArrayList<>();

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
}
