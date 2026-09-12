package org.n3gd0r.recipe.web.dtos.requests;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.usecase.random.RandomRecipeFiltersParameters;

public record RandomRecipeRequest(
        Integer cookTime,
        Integer instructionQuantity,
        Integer ingredientQuantity,
        List<String> ingredients,
        List<IngredientEnum> ingredientTypes) {

    public RandomRecipeFiltersParameters toRandomQuery(UUID recipeUserId) {
        return new RandomRecipeFiltersParameters(
                recipeUserId,
                Optional.ofNullable(cookTime),
                Optional.ofNullable(instructionQuantity),
                Optional.ofNullable(ingredientQuantity),
                Optional.ofNullable(ingredients),
                Optional.ofNullable(ingredientTypes));
    }
}
