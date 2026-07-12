package org.n3gd0r.recipe.domain.exception;

import org.n3gd0r.recipe.domain.RecipeStepId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * RecipeNotFoundException
 * throws when the recipe with a specific id was not found
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecipeStepNotFoundException extends RuntimeException {
    public RecipeStepNotFoundException(RecipeStepId id) {
        super("RecipeStep with id %s not found".formatted(id.getId()));
    }
}
