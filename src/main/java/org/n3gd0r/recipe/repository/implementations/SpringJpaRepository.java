package org.n3gd0r.recipe.repository.implementations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.n3gd0r.recipe.domain.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.PredicateSpecification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SpringJpaRepository extends CrudRepository<Recipe, UUID>,
        PagingAndSortingRepository<Recipe, UUID>, JpaSpecificationExecutor<Recipe> {
    Optional<Recipe> findByRecipeUserIdAndId(UUID recipeUserId, UUID id);

    Optional<Recipe> findByRecipeUserIdAndName(UUID recipeUserId, String name);

    Page<Recipe> findAllByRecipeUserId(UUID recipeUserId, Pageable pageable);

    List<Recipe> findAllByRecipeUserId(UUID recipeUserId, PredicateSpecification<Recipe> spec);

    long countByRecipeUserId(UUID recipeUserId);

    boolean existsByRecipeUserIdAndName(UUID recipeUserId, String name);

    boolean existsByRecipeUserIdAndId(UUID recipeUserId, UUID id);
}
