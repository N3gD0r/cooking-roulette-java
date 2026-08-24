package org.n3gd0r.recipe.usecase.exception;

import org.n3gd0r.commons.mediator.Request;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequestForHandlerNullPointerException extends RuntimeException {
    public RequestForHandlerNullPointerException(Class<? extends Request<?>> requestType) {
        super("Request %s was null".formatted(requestType.getSimpleName()));
    }
}
