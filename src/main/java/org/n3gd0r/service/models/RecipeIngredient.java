package org.n3gd0r.service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredient {
    private RecipeIngredientKey recipeIngredientKey;
    private float weight;
    private String weightMeasure;
}
