package org.n3gd0r.recipe.repository;

import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * SpringDataJpaRecipeRepository
 */
public interface SpringDataJpaRecipeRepository extends CrudRepository<Recipe, RecipeId>, PagingAndSortingRepository<Recipe, RecipeId> {

}
