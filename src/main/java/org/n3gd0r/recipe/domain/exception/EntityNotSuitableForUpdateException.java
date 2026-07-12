package org.n3gd0r.recipe.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * RecipeNotFoundException
 * throws when the recipe with a specific id was not found
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntityNotSuitableForUpdateException extends RuntimeException {
    public EntityNotSuitableForUpdateException(String updateEntityName) {
        super("Current object is not suitable for update operation, missing id for %s".formatted(updateEntityName));
    }
}
