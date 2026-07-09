package org.n3gd0r.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.n3gd0r.repository.entities.RecipeEntity;
import org.n3gd0r.repository.jparepos.RecipeJpaRepository;
import org.n3gd0r.repository.queries.RecipeQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class RecipeRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    private final RecipeJpaRepository jpaRepository;

    public Optional<RecipeEntity> findById(UUID id) {
        return jpaRepository.findById(id);
    }

    public List<RecipeEntity> findAll() {
        return jpaRepository.findAll();
    }

    @Transactional
    public void create(RecipeEntity recipe) {
        jpaRepository.save(recipe);
    }

    @Transactional
    public void upsert(UUID id, RecipeEntity recipe) {
        entityManager.createNativeQuery(RecipeQueries.MERGE_QUERY)
            .setParameter(1, id)
            .setParameter(2, recipe.getName())
            .setParameter(3, recipe.getCookTime())
            .setParameter(4, recipe.getStepsId())
            .setParameter(5, recipe.getIngredientsId())
            .executeUpdate();
    }

    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
