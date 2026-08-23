package org.n3gd0r.recipe.web.dtos.requests;

import java.util.List;
import java.util.Optional;

import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.usecase.random.RandomRecipeFiltersParameters;

public record RandomRecipeRequest(
        Optional<Integer> cookTime,
        Optional<Integer> instructionQuantity,
        Optional<Integer> ingredientQuantity,
        Optional<List<String>> ingredients,
        Optional<List<IngredientEnum>> ingredientTypes) {

    public RandomRecipeFiltersParameters toQuery(int pageSize) {
        return new RandomRecipeFiltersParameters(pageSize,
                cookTime,
                instructionQuantity,
                ingredientQuantity,
                ingredients,
                ingredientTypes);
    }
}
