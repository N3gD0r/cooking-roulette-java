package org.n3gd0r.recipe.usecase.get;

import java.util.UUID;

import org.n3gd0r.commons.mediator.Query;
import org.n3gd0r.recipe.domain.Recipe;
import org.springframework.util.Assert;

public record GetRecipeParameters(UUID recipeUserId, UUID id) implements Query<Recipe> {
    public GetRecipeParameters {
        Assert.notNull(id, "GetRecipeParameters id should not be null");
        Assert.notNull(recipeUserId, "GetRecipeParameters recipeUserId should not be null");
    }
}
