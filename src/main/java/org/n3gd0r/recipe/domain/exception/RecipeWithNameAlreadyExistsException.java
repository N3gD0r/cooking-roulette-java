package org.n3gd0r.recipe.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * RecipeWithNameAlreadyExistsException is thrown when creating a recipe with a name that already exists in the database
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RecipeWithNameAlreadyExistsException extends RuntimeException {
    public RecipeWithNameAlreadyExistsException(String name) {
        super("Recipe with %s already exists".formatted(name));
    }
}
