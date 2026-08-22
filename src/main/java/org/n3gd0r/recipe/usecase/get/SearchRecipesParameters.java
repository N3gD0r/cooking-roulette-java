package org.n3gd0r.recipe.usecase.get;

import java.util.List;
import java.util.Optional;

import org.n3gd0r.commons.mediator.Query;
import org.n3gd0r.recipe.domain.Recipe;

public record SearchRecipesParameters(Optional<String> name, Optional<Integer> cookTime, int page, int size)
        implements Query<List<Recipe>> {
}
