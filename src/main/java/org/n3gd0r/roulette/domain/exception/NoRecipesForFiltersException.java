package org.n3gd0r.roulette.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoRecipesForFiltersException extends RuntimeException {
    public NoRecipesForFiltersException() {
        super("Cero recipes matched");
    }
}
