package org.n3gd0r.repository.entities;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeIngredientKeyEntity implements Serializable {
    @Column(name = "recipe_id", nullable = false)
    private UUID recipeId;

    @Column(name = "ingredient_id", nullable = false)
    private UUID ingredientId;
}
