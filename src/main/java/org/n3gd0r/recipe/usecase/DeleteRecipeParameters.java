package org.n3gd0r.recipe.usecase;

import org.springframework.util.Assert;

import java.util.UUID;

/**
 * DeleteRecipeParameters
 */
public record DeleteRecipeParameters(UUID id) {
    public DeleteRecipeParameters {
        Assert.notNull(id, "The DeleteRecipeParameters id should not be empty");
    }
}

