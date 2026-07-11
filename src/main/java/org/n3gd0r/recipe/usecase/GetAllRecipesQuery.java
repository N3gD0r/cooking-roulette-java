package org.n3gd0r.recipe.usecase;

import org.n3gd0r.infrastructure.mediator.Query;
import org.n3gd0r.recipe.domain.Recipe;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * GetAllRecipesQuery
 */
public class GetAllRecipesQuery extends Query<List<Recipe>> {
    public Pageable pagination;

    public GetAllRecipesQuery(GetAllRecipesParameters parameters) {
        pagination = PageRequest.of(parameters.page(), parameters.size());
    }
}

