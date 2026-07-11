package org.n3gd0r.recipe.repository;

import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeId;
import org.n3gd0r.recipe.domain.RecipeIngredientId;
import org.n3gd0r.recipe.domain.RecipeStepId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * RecipeRepository
 */
public interface RecipeRepository {
    RecipeId nextId();

    RecipeIngredientId nextRecipeIngredientId();

    RecipeStepId nextRecipeStepId();

    Optional<Recipe> findById(RecipeId id);

    Recipe findByName(String name);

    void save(Recipe recipe);

    Recipe getById(RecipeId id);

    void validateExistsById(RecipeId recipeId);
    void validateNameUnique(String name);

    Page<Recipe> findAll(Pageable pageable);

    void deleteAll();

    void deleteById(RecipeId recipeId);
}
