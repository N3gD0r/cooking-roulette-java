package org.n3gd0r.recipe.usecase.records;

import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Mass;
import org.n3gd0r.recipe.domain.RecipeIngredientId;

public record PatchIngredientParameters(RecipeIngredientId id,
        String ingredientName,
        IngredientEnum ingredientType,
        Mass weight) {

    public boolean canAddIngredient() {
        return ingredientName != null && ingredientType != null && weight != null && id == null;
    }
}
