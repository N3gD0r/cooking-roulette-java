package org.n3gd0r.recipe.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.n3gd0r.infrastructure.test.RecipeDataJpaTest;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeIngredient;
import org.n3gd0r.recipe.domain.RecipeIngredientMother;
import org.n3gd0r.recipe.domain.RecipeInstruction;
import org.n3gd0r.recipe.domain.RecipeInstructionMother;
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

    @Test
    void testSaveRecipeWithUserId() {
        UUID id = repository.nextId();
        UUID recipeUserId = UUID.randomUUID();
        Recipe recipe = RecipeMother.recipe()
                .id(id)
                .userId(recipeUserId)
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

    @Test
    void testSaveRecipeWithNestedRelationships() {
        UUID id = repository.nextId();
        UUID recipeUserId = UUID.randomUUID();
        RecipeIngredient recipeIngredient = RecipeIngredientMother.recipeIngredient()
                .build();
        RecipeInstruction recipeInstruction = RecipeInstructionMother.recipeInstruction()
                .build();
        Recipe recipe = RecipeMother.recipe()
                .id(id)
                .userId(recipeUserId)
                .name("huevos a la mexicana")
                .cookTime(30)
                .ingredients(Collections.singletonList(recipeIngredient))
                .instructions(Collections.singletonList(recipeInstruction))
                .build();

        repository.save(recipe);
        entityManager.flush();
        entityManager.clear();

        UUID savedId = jdbcClient.sql("SELECT id FROM recipe").query(UUID.class).single();
        UUID savedUserId = jdbcClient.sql("SELECT recipe_user_id FROM recipe").query(UUID.class).single();
        UUID recipeIngredientIdFromRecipe = jdbcClient.sql(
                "SELECT ri.recipe_id FROM recipe_ingredient AS ri INNER JOIN recipe AS r ON r.id = ri.recipe_id WHERE ri.recipe_id = r.id")
                .query(UUID.class).single();
        UUID recipeInstructionIdFromRecipe = jdbcClient.sql(
                "SELECT ri.recipe_id FROM recipe_instruction AS ri INNER JOIN recipe AS r ON r.id = ri.recipe_id WHERE ri.recipe_id = r.id")
                .query(UUID.class).single();

        assertEquals(id, savedId);
        assertEquals(recipeUserId, savedUserId);
        assertEquals(id, recipeIngredientIdFromRecipe);
        assertEquals(id, recipeInstructionIdFromRecipe);
    }
}
