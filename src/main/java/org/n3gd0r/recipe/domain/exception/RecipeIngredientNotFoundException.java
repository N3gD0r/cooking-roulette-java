package org.n3gd0r.recipe.domain.exception;

import org.n3gd0r.recipe.domain.RecipeIngredientId;

/**
 * RecipeIngredientNotFoundException
 * throws when the recipe ingredient with a specific id was not found
 */
public class RecipeIngredientNotFoundException extends RuntimeException {
    public RecipeIngredientNotFoundException(RecipeIngredientId id) {
        super("RecipeIngredient with id %s not found".formatted(id.getId()));
    }
}
