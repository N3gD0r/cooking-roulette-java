package org.n3gd0r.service.abs;

import java.util.List;
import java.util.Optional;

public interface AllService<T, K>{
    Optional<T> get(K id);

    List<T> get();

    void create(T entity);

    void upsert(K id, T entity);

    void delete(K id);
}
