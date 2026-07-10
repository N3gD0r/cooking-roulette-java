package org.n3gd0r.recipe.repository;

import java.util.Optional;

import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeId;
import org.n3gd0r.recipe.domain.RecipeIngredientId;
import org.n3gd0r.recipe.domain.RecipeStepId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * RecipeRepository
 */
public interface RecipeRepository {
    RecipeId nextId();

    RecipeIngredientId nextRecipeIngredientId();

    RecipeStepId nextRecipeStepId();

    Optional<Recipe> findById(RecipeId id);

    void save(Recipe recipe);

    Recipe getbyId(RecipeId id);

    void validateExistsById(RecipeId recipeId);

    Page<Recipe> findAll(Pageable pageable);

    void deleteAll();

    void deleteById(RecipeId recipeId);
}
