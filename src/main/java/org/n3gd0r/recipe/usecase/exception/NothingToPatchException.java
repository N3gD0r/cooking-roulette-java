package org.n3gd0r.recipe.usecase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NothingToPatchException extends RuntimeException {
    public NothingToPatchException() {
        super("Cannot update recipe with missing fields");
    }
}
