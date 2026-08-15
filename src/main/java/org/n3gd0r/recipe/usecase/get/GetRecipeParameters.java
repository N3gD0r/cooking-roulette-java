package org.n3gd0r.recipe.usecase.get;

import org.n3gd0r.commons.mediator.Query;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeId;

/**
 * GetRecipeQuery
 */
public record GetRecipeParameters(RecipeId id, String name) implements Query<Recipe> {
}
