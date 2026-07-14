package org.n3gd0r.recipe.usecase;

import org.n3gd0r.infrastructure.mediator.Query;
import org.n3gd0r.recipe.domain.Recipe;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import java.util.List;

/**
 * GetAllRecipesQuery
 */
public class GetAllRecipesParameters extends Query<List<Recipe>> {
    public Pageable pagination;

    public GetAllRecipesParameters() {
        pagination = PageRequest.of(0, 20);
    }

    public GetAllRecipesParameters(int page, int size) {
        Assert.isTrue(page >= 0, "The Pagination parameter page should not be a negative number");
        Assert.isTrue(size > 0, "The Pagination parameter size should be a positive number");
        pagination = PageRequest.of(page, size);
    }
}
