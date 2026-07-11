package org.n3gd0r.infrastructure.mediator;

/**
 * Mediator
 */
public interface Mediator {
    <T extends Request<R>, R> R send(T request) throws RuntimeException;

    <T extends Request<R>, R> void registerHandler(Class<T> requestType, RequestHandler<T, R> handler);
}
