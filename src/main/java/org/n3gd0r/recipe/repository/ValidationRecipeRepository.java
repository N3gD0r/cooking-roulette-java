package org.n3gd0r.recipe.repository;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ValidationRecipeRepository {
    void validateExistsById(UUID id);

    void validateNameUnique(String name);
}
