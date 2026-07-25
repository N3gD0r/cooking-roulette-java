package org.n3gd0r.recipe.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.n3gd0r.infrastructure.test.RecipeDataJpaTest;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeId;
import org.n3gd0r.recipe.domain.RecipeMother;
import org.n3gd0r.recipe.repository.implementations.JpaRecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;

import jakarta.persistence.EntityManager;

@RecipeDataJpaTest
public class JpaRecipeRepositoryTest {
    @Autowired
    private JpaRecipeRepository repository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private JdbcClient jdbcClient;

    @Test
    void testSaveSimpleRecipe() {
        RecipeId recipeId = repository.nextId();
        Recipe recipe = RecipeMother.recipe()
                .id(recipeId)
                .name("huevos a la mexicana")
                .cookTime(30)
                .ingredients(Collections.emptyList())
                .instructions(Collections.emptyList())
                .build();

        repository.save(recipe);
        entityManager.flush();
        entityManager.clear();

        assertEquals(recipeId.getId(), jdbcClient.sql("SELECT id from recipe").query(UUID.class).single());
        assertEquals("huevos a la mexicana", jdbcClient.sql("SELECT name from recipe").query(String.class).single());
        assertEquals(30, jdbcClient.sql("SELECT cook_time from recipe").query(Integer.class).single());
    }
}
