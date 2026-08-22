package org.n3gd0r.recipe.usecase.get;

import java.util.UUID;

import org.n3gd0r.commons.mediator.Query;
import org.n3gd0r.recipe.domain.Recipe;

/**
 * GetRecipeQuery
 */
public record GetRecipeParameters(UUID id, String name) implements Query<Recipe> {
    public static GetRecipeParameters byId(UUID id) {
        return new GetRecipeParameters(id, null);
    }

    public static GetRecipeParameters byName(String name) {
        return new GetRecipeParameters(null, name);
    }
}
