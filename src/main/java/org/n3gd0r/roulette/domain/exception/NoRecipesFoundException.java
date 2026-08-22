package org.n3gd0r.roulette.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoRecipesFoundException extends RuntimeException {
    public NoRecipesFoundException() {
        super("No recipes found");
    }
}
