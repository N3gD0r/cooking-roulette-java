package org.n3gd0r.repository.jparepos;

import java.util.UUID;

import org.n3gd0r.repository.entities.RecipeStepEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeStepJpaRepository extends JpaRepository<RecipeStepEntity, UUID> {
}
