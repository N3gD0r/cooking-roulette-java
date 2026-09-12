package org.n3gd0r.recipe.repository;

import java.util.UUID;

import org.n3gd0r.recipe.domain.Recipe;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface WriteRecipeRepository {
    void save(Recipe recipe);

    void deleteAll();

    void deleteById(UUID recipeUserId, UUID id);
}
