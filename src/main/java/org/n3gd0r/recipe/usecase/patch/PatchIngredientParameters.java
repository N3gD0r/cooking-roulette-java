package org.n3gd0r.recipe.usecase.patch;

import java.util.UUID;

import org.n3gd0r.recipe.domain.IngredientEnum;

public record PatchIngredientParameters(UUID id,
        String ingredientName,
        IngredientEnum ingredientType,
        Integer weightInGrams) {

    public boolean canAddIngredient() {
        return ingredientName != null && ingredientType != null && weightInGrams != null && id == null;
    }
}
