package org.n3gd0r.repository.jparepos;

import java.util.UUID;

import org.n3gd0r.repository.entities.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientJpaRepository extends JpaRepository<IngredientEntity, UUID> {
}
