package org.n3gd0r.repository.jparepos;

import org.n3gd0r.repository.entities.RecipeIngredientEntity;
import org.n3gd0r.repository.entities.RecipeIngredientKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeIngredientEntityJpaRepository
        extends JpaRepository<RecipeIngredientEntity, RecipeIngredientKeyEntity> {
}
