package org.n3gd0r.roulette.web;

import java.util.List;
import java.util.Optional;

import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.roulette.usecase.RandomRecipeFiltersParameters;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RandomRecipeRequest(
        @Valid @NotNull @Positive Integer pageSize,
        Optional<String> name,
        @Valid @Positive Optional<Integer> cookTime,
        Optional<Integer> instructionQuantity,
        Optional<Integer> ingredientQuantity,
        Optional<List<String>> ingredients,
        Optional<List<IngredientEnum>> ingredientTypes) {

    public RandomRecipeFiltersParameters toQuery() {
        return new RandomRecipeFiltersParameters(pageSize,
                name,
                cookTime,
                instructionQuantity,
                ingredientQuantity,
                ingredients,
                ingredientTypes);
    }
}
