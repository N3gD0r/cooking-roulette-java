package org.n3gd0r.recipe.repository.implementations;

import java.util.Optional;
import java.util.UUID;

import org.n3gd0r.recipe.domain.Recipe;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SpringJpaRepository extends CrudRepository<Recipe, UUID>,
        PagingAndSortingRepository<Recipe, UUID>, JpaSpecificationExecutor<Recipe> {
    Optional<Recipe> getRecipeByName(String name);

    boolean existsByName(String name);
}
