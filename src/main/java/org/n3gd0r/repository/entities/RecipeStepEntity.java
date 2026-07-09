package org.n3gd0r.repository.entities;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recipe_steps")
public class RecipeStepEntity {
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private int step;
    private String instruction;
    private UUID recipeId;
}
