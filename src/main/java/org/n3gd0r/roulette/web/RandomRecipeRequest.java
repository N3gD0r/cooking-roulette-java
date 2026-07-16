package org.n3gd0r.roulette.web;

import java.util.List;
import java.util.Optional;

import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.roulette.usecase.RandomRecipeFiltersParameters;

public record RandomRecipeRequest(
        Optional<String> name,
        Optional<Integer> cookTime,
        Optional<Integer> instructionQuantity,
        Optional<Integer> ingredientQuantity,
        Optional<List<String>> ingredients,
        Optional<List<IngredientEnum>> ingredientTypes) {

    public RandomRecipeFiltersParameters toQuery(int pageSize) {
        return new RandomRecipeFiltersParameters(pageSize,
                name,
                cookTime,
                instructionQuantity,
                ingredientQuantity,
                ingredients,
                ingredientTypes);
    }
}
