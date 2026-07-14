package org.n3gd0r.recipe.usecase;

import org.n3gd0r.infrastructure.mediator.Query;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeId;
import org.springframework.util.Assert;

/**
 * GetRecipeQuery
 */
public class GetRecipeParameters extends Query<Recipe> {
    private final String name;
    private final RecipeId id;

    public GetRecipeParameters(String name) {
        Assert.hasText(name, "The GetRecipeQuery name should have text");
        this.name = name;
        id = null;
    }

    public GetRecipeParameters(RecipeId id) {
        Assert.notNull(id, "The GetRecipeQuery id should not be null");
        this.id = id;
        this.name = null;
    }

    public String name() {
        return name;
    }

    public RecipeId id() {
        return id;
    }
}
