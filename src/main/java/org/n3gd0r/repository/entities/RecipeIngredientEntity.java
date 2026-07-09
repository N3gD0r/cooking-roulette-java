package org.n3gd0r.repository.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recipes_ingredients")
public class RecipeIngredientEntity {
    @EmbeddedId
    private RecipeIngredientKeyEntity recipeIngredientKey;
    private float weight;
    private String weightMeasure;
}
