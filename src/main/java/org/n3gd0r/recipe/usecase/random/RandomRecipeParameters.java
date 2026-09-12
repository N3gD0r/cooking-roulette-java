package org.n3gd0r.recipe.usecase.random;

import java.util.UUID;

import org.n3gd0r.commons.mediator.Query;
import org.n3gd0r.recipe.domain.Recipe;
import org.springframework.util.Assert;

public record RandomRecipeParameters(UUID recipeUserId) implements Query<Recipe> {
    public RandomRecipeParameters {
        Assert.notNull(recipeUserId, "The RandomRecipeParameters recipeUserId should not be null");
    }
}
