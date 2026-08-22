package org.n3gd0r.recipe.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RecipeNameIsEmptyException extends RuntimeException {
    public RecipeNameIsEmptyException() {
        super("The recipe name cannot be empty");
    }
}
