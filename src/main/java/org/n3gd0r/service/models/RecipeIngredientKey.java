package org.n3gd0r.service.models;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientKey {
    private UUID recipeId;
    private UUID ingredientId;
}
