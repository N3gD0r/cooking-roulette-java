package org.n3gd0r.recipe.usecases;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Mass;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeId;
import org.n3gd0r.recipe.domain.RecipeIngredient;
import org.n3gd0r.recipe.domain.RecipeIngredientId;
import org.n3gd0r.recipe.domain.RecipeInstruction;
import org.n3gd0r.recipe.domain.RecipeInstructionId;
import org.n3gd0r.recipe.domain.RecipeMother;
import org.n3gd0r.recipe.repository.InMemoryRecipeRepository;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.n3gd0r.recipe.usecase.get.GetAllRecipesParameters;
import org.n3gd0r.recipe.usecase.get.GetAllRecipesQuery;

public class GetAllRecipesTest {
    private RecipeRepository recipeRepository;
    private GetAllRecipesQuery getAllRecipesQuery;

    @BeforeEach
    void setUp() {
        recipeRepository = new InMemoryRecipeRepository();
        getAllRecipesQuery = new GetAllRecipesQuery(recipeRepository);

        List<RecipeIngredient> ingredients = Arrays.asList(
                new RecipeIngredient(new RecipeIngredientId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6")),
                        "huevos",
                        IngredientEnum.CARNES,
                        Mass.ofGrams(180)));

        List<RecipeInstruction> instructions = Arrays.asList(
                new RecipeInstruction(new RecipeInstructionId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6")),
                        1,
                        "En agua hirviendo, colocar los huevos durante 15 minutos."));

        Recipe recipe = RecipeMother.recipe()
                .id(new RecipeId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6")))
                .name("huevos cocidos")
                .cookTime(20)
                .ingredients(ingredients)
                .instructions(instructions)
                .build();
        recipeRepository.save(recipe);
    }

    @Test
    void testGetAllRecipes() {
        GetAllRecipesParameters parameters = new GetAllRecipesParameters();

        List<Recipe> recipes = getAllRecipesQuery.execute(parameters);

        assertThat(recipes).isNotNull().hasSize(1);
    }

    @Test
    void testGetAllRecipesReturnsEmptyList() {
        recipeRepository.deleteAll();
        GetAllRecipesParameters parameters = new GetAllRecipesParameters();

        List<Recipe> recipes = getAllRecipesQuery.execute(parameters);

        assertThat(recipes).isNotNull().hasSize(0);
    }
}
