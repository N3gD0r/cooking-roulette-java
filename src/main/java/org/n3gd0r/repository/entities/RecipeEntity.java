package org.n3gd0r.repository.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recipes")
public class RecipeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "name", length = 255, nullable = false)
    private String name;
    @Column(name = "cook_time", nullable = false)
    private int cookTime;
    @Column(name = "ingredients_id", nullable = false)
    private UUID ingredientsId;
    @Column(name = "steps_id", nullable = false)
    private UUID stepsId;
}
