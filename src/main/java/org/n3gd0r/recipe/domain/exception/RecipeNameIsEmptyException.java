package org.n3gd0r.recipe.domain.exception;

public class RecipeNameIsEmptyException extends RuntimeException {
    public RecipeNameIsEmptyException() {
        super("The recipe name cannot be empty");
    }
}
