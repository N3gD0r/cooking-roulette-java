package org.n3gd0r.recipe.repository;

import java.util.List;
import java.util.UUID;

import org.n3gd0r.recipe.domain.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ReadRecipeRepository {
    Recipe getById(UUID recipeUserId, UUID id);

    Recipe getByName(UUID recipeUserId, String name);

    Page<Recipe> findAll(UUID recipeUserId, Pageable pageable);

    List<Recipe> findAll(UUID recipeUserId, FilterQuery spec);

    long count(UUID recipeUserId);
}
