package org.n3gd0r.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.n3gd0r.repository.entities.IngredientEntity;
import org.n3gd0r.repository.jparepos.IngredientJpaRepository;
import org.n3gd0r.repository.queries.IngredientQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class IngredientRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    private final IngredientJpaRepository jpaRepository;

    public Optional<IngredientEntity> findById(UUID id) {
        return jpaRepository.findById(id);
    }

    public List<IngredientEntity> findAll() {
        return jpaRepository.findAll();
    }

    @Transactional
    public void create(IngredientEntity ingredient) {
        jpaRepository.save(ingredient);
    }

    @Transactional
    public void upsert(UUID id, IngredientEntity ingredient) {
        entityManager.createNativeQuery(IngredientQueries.MERGE_QUERY)
                .setParameter(1, id)
                .setParameter(2, ingredient.getName())
                .setParameter(3, ingredient.getTypeId())
                .executeUpdate();
    }

    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
