package org.n3gd0r.infrastructure.mediator;

public class RequestNullException extends RuntimeException {
    public RequestNullException() {
        super("The mediator cannot send a null request");
    }
}
