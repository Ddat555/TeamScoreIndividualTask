package org.teamscore.individualTask.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    private String color;
    private String description;
    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
    private List<Cost> costs = new ArrayList<>();

    public Category(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public Category(String name, String color, String description) {
        this.name = name;
        this.color = color;
        this.description = description;
    }

    public Category(Long id, String name, String color, String description) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.description = description;
    }

    public boolean isUseInCosts(){
        return !costs.isEmpty();
    }
}
