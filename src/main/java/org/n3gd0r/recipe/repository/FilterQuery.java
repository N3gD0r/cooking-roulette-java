package org.n3gd0r.recipe.repository;

import java.util.List;
import java.util.Optional;

import org.n3gd0r.recipe.domain.IngredientEnum;

public record FilterQuery(
        Optional<String> name,
        Optional<Integer> cookTime,
        Optional<Integer> instructionQuantity,
        Optional<Integer> ingredientQuantity,
        Optional<List<String>> ingredients,
        Optional<List<IngredientEnum>> ingredientTypes) {

    public boolean noFilters() {
        return (name.isEmpty() ? true : name.get().isBlank()) &&
                (cookTime.isEmpty() ? true : cookTime.get() < 1) &&
                instructionQuantity.isEmpty() &&
                ingredientQuantity.isEmpty() &&
                (ingredients.isEmpty() ? true : ingredients.get().size() == 0) &&
                (ingredientTypes.isEmpty() ? true : ingredientTypes.get().size() == 0);
    }
}
