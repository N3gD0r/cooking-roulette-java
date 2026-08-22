package org.n3gd0r.roulette.usecase;

import java.util.List;
import java.util.Optional;

import org.n3gd0r.commons.mediator.Query;
import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Recipe;
import org.springframework.util.Assert;

public record RandomRecipeFiltersParameters(int pageSize, Optional<Integer> cookTime,
        Optional<Integer> instructionQuantity, Optional<Integer> ingredientQuantity, Optional<List<String>> ingredients,
        Optional<List<IngredientEnum>> ingredientTypes) implements Query<Recipe> {

    public RandomRecipeFiltersParameters {
        Assert.isTrue(pageSize > 0, "The RandomRecipeFiltersParameters pageSize should be a positive number");
    }

    public boolean isEmptyRequest() {
        return cookTime.isEmpty() && instructionQuantity.isEmpty() && ingredientQuantity.isEmpty()
                && ingredients.isEmpty() && ingredientTypes().isEmpty();
    }
}
