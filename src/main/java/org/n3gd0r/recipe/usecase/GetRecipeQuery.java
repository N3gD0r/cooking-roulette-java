package org.n3gd0r.recipe.usecase;

import org.n3gd0r.infrastructure.mediator.Query;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeId;

/**
 * GetRecipeQuery
 */
public class GetRecipeQuery extends Query<Recipe> {
    private final String name;
    private final RecipeId id;

    public GetRecipeQuery(String name) {
        this.name = name;
        this.id = null;
    }

    public GetRecipeQuery(RecipeId id) {
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
