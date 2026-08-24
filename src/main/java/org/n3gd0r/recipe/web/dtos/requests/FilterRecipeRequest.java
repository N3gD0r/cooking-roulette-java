package org.n3gd0r.recipe.web.dtos.requests;

import java.util.List;
import java.util.Optional;

import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.usecase.get.SearchRecipesParameters;

public record FilterRecipeRequest(
        String name,
        Integer cookTime,
        Integer instructionQuantity,
        Integer ingredientQuantity,
        List<String> ingredients,
        List<IngredientEnum> ingredientTypes) {

    public SearchRecipesParameters toQuery() {
        return new SearchRecipesParameters(
                Optional.ofNullable(name),
                Optional.ofNullable(cookTime),
                Optional.ofNullable(instructionQuantity),
                Optional.ofNullable(ingredientQuantity),
                Optional.ofNullable(ingredients),
                Optional.ofNullable(ingredientTypes));
    }
}
