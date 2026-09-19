package org.n3gd0r.recipe.domain.exception;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoRecipesFoundException extends RuntimeException {
    public NoRecipesFoundException(UUID recipeUserId) {
        super("No recipes found for user: %s".formatted(recipeUserId));
    }
}
