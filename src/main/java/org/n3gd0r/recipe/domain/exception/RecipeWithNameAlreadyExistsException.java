package org.n3gd0r.recipe.domain.exception;

/**
 * RecipeWithNameAlreadyExistsException is thrown when creating a recipe with a
 * name that already exists in the database
 */
public class RecipeWithNameAlreadyExistsException extends RuntimeException {
    public RecipeWithNameAlreadyExistsException(String name) {
        super("Recipe with %s already exists".formatted(name));
    }
}
