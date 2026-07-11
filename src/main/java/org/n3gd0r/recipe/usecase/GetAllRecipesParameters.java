package org.n3gd0r.recipe.usecase;

import org.springframework.util.Assert;

/**
 * GetAllRecipesParameters
 */
public record GetAllRecipesParameters(int page, int size) {
    public GetAllRecipesParameters {
        Assert.isTrue(page >= 0, "The Pagination parameter page should not be a negative number");
        Assert.isTrue(size > 0, "The Pagination parameter size should be a positive number");
    }
}

