package org.n3gd0r.recipe.usecase.patch;

import java.util.UUID;

import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Mass;

public record PatchIngredientParameters(UUID id,
        String ingredientName,
        IngredientEnum ingredientType,
        Mass weight) {

    public boolean canAddIngredient() {
        return ingredientName != null && ingredientType != null && weight != null && id == null;
    }
}
