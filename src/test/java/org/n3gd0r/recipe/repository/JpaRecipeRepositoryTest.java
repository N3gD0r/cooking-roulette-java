package org.n3gd0r.recipe.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.n3gd0r.infrastructure.test.RecipeDataJpaTest;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeMother;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;

import jakarta.persistence.EntityManager;

@RecipeDataJpaTest
public class JpaRecipeRepositoryTest {
    @Autowired
    private RecipeRepository repository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private JdbcClient jdbcClient;

    @Test
    void testSaveSimpleRecipe() {
        UUID id = repository.nextId();
        Recipe recipe = RecipeMother.recipe()
                .id(id)
                .name("huevos a la mexicana")
                .cookTime(30)
                .ingredients(Collections.emptyList())
                .instructions(Collections.emptyList())
                .build();

        repository.save(recipe);
        entityManager.flush();
        entityManager.clear();

        UUID savedId = jdbcClient.sql("SELECT id from recipe").query(UUID.class).single();
        String savedName = jdbcClient.sql("SELECT name from recipe").query(String.class).single();
        int savedCookTime = jdbcClient.sql("SELECT cook_time from recipe").query(Integer.class).single();

        assertEquals(id, savedId);
        assertEquals("huevos a la mexicana", savedName);
        assertEquals(30, savedCookTime);
    }
}
