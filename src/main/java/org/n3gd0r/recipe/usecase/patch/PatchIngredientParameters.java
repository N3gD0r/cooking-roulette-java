package org.n3gd0r.recipe.usecase.patch;

import java.util.Optional;
import java.util.UUID;

import org.n3gd0r.recipe.domain.IngredientEnum;

public record PatchIngredientParameters(
        Optional<UUID> id,
        Optional<String> ingredientName,
        Optional<IngredientEnum> ingredientType,
        Optional<Integer> weightInGrams) {

    public boolean canAddIngredient() {
        return id.isEmpty() &&
                ingredientName.isPresent() &&
                ingredientType.isPresent() &&
                weightInGrams.isPresent();
    }
}
