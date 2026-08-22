package org.n3gd0r.recipe.domain.exception;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * RecipeInstructionNotFoundException
 * throws when the recipe instruction with a specific id was not found
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecipeInstructionNotFoundException extends RuntimeException {
    public RecipeInstructionNotFoundException(UUID id) {
        super("RecipeInstruction with id %s not found".formatted(id.toString()));
    }
}
