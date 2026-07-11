package org.n3gd0r.infrastructure.mediator;

/**
 * RequestHandler
 */
public interface RequestHandler<T extends Request<R>, R> {
    R execute(T request);
}
