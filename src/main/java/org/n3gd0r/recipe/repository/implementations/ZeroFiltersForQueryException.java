package org.n3gd0r.recipe.repository.implementations;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ZeroFiltersForQueryException extends RuntimeException {
    public ZeroFiltersForQueryException() {
        super("Got 0 filters to perform a search query");
    }
}
