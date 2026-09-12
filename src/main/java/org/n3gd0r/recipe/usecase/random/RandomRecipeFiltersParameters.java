package org.n3gd0r.recipe.usecase.random;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.n3gd0r.commons.mediator.Query;
import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.repository.FilterQuery;

public record RandomRecipeFiltersParameters(
        UUID recipeUserId,
        Optional<Integer> cookTime,
        Optional<Integer> instructionQuantity,
        Optional<Integer> ingredientQuantity,
        Optional<List<String>> ingredients,
        Optional<List<IngredientEnum>> ingredientTypes) implements Query<Recipe> {

    public boolean isEmptyRequest() {
        return (cookTime.isEmpty() ? true : cookTime.get() < 1) &&
                instructionQuantity.isEmpty() &&
                ingredientQuantity.isEmpty() &&
                (ingredients.isEmpty() ? true : ingredients.get().size() == 0) &&
                (ingredientTypes.isEmpty() ? true : ingredientTypes.get().size() == 0);
    }

    public FilterQuery toFilterQuery() {
        return new FilterQuery(
                Optional.empty(),
                cookTime,
                instructionQuantity,
                ingredientQuantity,
                ingredients,
                ingredientTypes);
    }
}
