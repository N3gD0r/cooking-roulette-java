package org.n3gd0r.recipe.domain.exception;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * RecipeNotFoundException
 * throws when the recipe with a specific id was not found
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException(UUID id) {
        super("Recipe with id %s not found".formatted(id.toString()));
    }

    public RecipeNotFoundException(String name) {
        super("Recipe with name %s not found".formatted(name));
    }

    public RecipeNotFoundException() {
        super("Recipe not found, check the query parameters used");
    }
}
