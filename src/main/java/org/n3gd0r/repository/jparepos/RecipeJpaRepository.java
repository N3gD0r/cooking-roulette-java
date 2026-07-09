package org.n3gd0r.repository.jparepos;

import java.util.UUID;

import org.n3gd0r.repository.entities.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeJpaRepository extends JpaRepository<RecipeEntity, UUID> {
}
