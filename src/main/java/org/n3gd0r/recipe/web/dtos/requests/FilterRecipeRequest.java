package org.n3gd0r.recipe.web.dtos.requests;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.usecase.get.SearchRecipesParameters;

public record FilterRecipeRequest(
        String name,
        Integer cookTime,
        Integer instructionQuantity,
        Integer ingredientQuantity,
        List<String> ingredients,
        List<IngredientEnum> ingredientTypes) {

    public SearchRecipesParameters toQuery(UUID recipeUserId) {
        return new SearchRecipesParameters(
                recipeUserId,
                Optional.ofNullable(name),
                Optional.ofNullable(cookTime),
                Optional.ofNullable(instructionQuantity),
                Optional.ofNullable(ingredientQuantity),
                Optional.ofNullable(ingredients),
                Optional.ofNullable(ingredientTypes));
    }
}
