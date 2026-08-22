package org.n3gd0r.recipe.repository;

import java.util.List;
import java.util.UUID;

import org.n3gd0r.recipe.domain.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.PredicateSpecification;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ReadRecipeRepository {
    Recipe getById(UUID id);

    Recipe getByName(String name);

    Page<Recipe> findAll(Pageable pageable);

    List<Recipe> findAll(PredicateSpecification<Recipe> spec);

    long count();
}
