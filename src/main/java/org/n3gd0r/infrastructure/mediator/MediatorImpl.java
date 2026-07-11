package org.n3gd0r.infrastructure.mediator;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * MediatorImpl
 */
@Component
public class MediatorImpl implements Mediator {
    private final Map<Class<?>, RequestHandler<?, ?>> handlers;

    MediatorImpl() {
        this.handlers = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Request<R>, R> R send(T request) throws RuntimeException {
        Class<?> requestType = request.getClass();
        if (!handlers.containsKey(requestType)) {
            throw new IllegalArgumentException(String.format("Handler %s not found in known handlers for this mediator.", requestType));
        }
        RequestHandler<T, R> handler = (RequestHandler<T, R>) handlers.get(requestType);
        return handler.execute(request);
    }

    @Override
    public <T extends Request<R>, R> void registerHandler(Class<T> requestType, RequestHandler<T, R> handler) {
        handlers.put(requestType, handler);
    }
}
