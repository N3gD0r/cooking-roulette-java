package org.n3gd0r.recipe.domain.exception;

import org.n3gd0r.recipe.domain.RecipeId;

/**
 * RecipeNotFoundException
 * throws when the recipe with a specific id was not found
 */
public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException(RecipeId id) {
        super("Recipe with id %s not found".formatted(id.getId()));
    }

    public RecipeNotFoundException(String name) {
        super("Recipe with name %s not found".formatted(name));
    }

    public RecipeNotFoundException() {
        super("Recipe not found, check the query parameters used");
    }
}
