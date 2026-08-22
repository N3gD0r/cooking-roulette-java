package org.n3gd0r.recipe.usecase.get;

import org.n3gd0r.commons.mediator.Query;
import org.n3gd0r.recipe.domain.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * GetAllRecipesQuery
 */
public record GetAllRecipesParameters(Pageable pageable) implements Query<Page<Recipe>> {
}
