package org.n3gd0r.recipe.usecase.get;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.n3gd0r.commons.mediator.Query;
import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.repository.FilterQuery;

public record SearchRecipesParameters(
        UUID recipeUserId,
        Optional<String> name,
        Optional<Integer> cookTime,
        Optional<Integer> instructionQuantity,
        Optional<Integer> ingredientQuantity,
        Optional<List<String>> ingredients,
        Optional<List<IngredientEnum>> ingredientTypes) implements Query<List<Recipe>> {
    public FilterQuery toFilterQuery() {
        return new FilterQuery(name, cookTime, instructionQuantity, ingredientQuantity, ingredients, ingredientTypes);
    }
}
