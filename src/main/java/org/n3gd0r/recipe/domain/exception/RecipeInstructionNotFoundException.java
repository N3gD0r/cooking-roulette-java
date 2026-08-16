package org.n3gd0r.recipe.domain.exception;

import org.n3gd0r.recipe.domain.RecipeInstructionId;

/**
 * RecipeInstructionNotFoundException
 * throws when the recipe instruction with a specific id was not found
 */
public class RecipeInstructionNotFoundException extends RuntimeException {
    public RecipeInstructionNotFoundException(RecipeInstructionId id) {
        super("RecipeInstruction with id %s not found".formatted(id.getId()));
    }
}
