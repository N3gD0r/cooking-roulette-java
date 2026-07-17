package org.n3gd0r.recipe.usecases;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Mass;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeIngredient;
import org.n3gd0r.recipe.domain.RecipeIngredientMother;
import org.n3gd0r.recipe.domain.RecipeInstruction;
import org.n3gd0r.recipe.domain.RecipeInstructionMother;
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
                RecipeIngredientMother.recipeIngredient()
                        .ingredientName("huevos")
                        .ingredientType(IngredientEnum.CARNES)
                        .weight(Mass.ofGrams(180))
                        .build(),
                RecipeIngredientMother.recipeIngredient()
                        .ingredientName("agua")
                        .ingredientType(IngredientEnum.ALIMENTOS_LIBRES_DE_ENERGIA)
                        .weight(Mass.ofGrams(500))
                        .build());

        List<RecipeInstruction> instructions = Arrays.asList(
                RecipeInstructionMother.recipeInstruction()
                        .instructionNumber(1)
                        .instruction("Hervir el agua y colocar los huevos durante 15 minutos")
                        .build(),
                RecipeInstructionMother.recipeInstruction()
                        .instructionNumber(2)
                        .instruction("Retirar los huevos y colocarlos en agua fria con hielos por 5 minutos")
                        .build());

        Recipe recipe = RecipeMother.recipe()
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
    void testGetAllRecipesAfterDeleteOperationReturnsEmptyList() {
        recipeRepository.deleteAll();
        GetAllRecipesParameters parameters = new GetAllRecipesParameters();

        List<Recipe> recipes = getAllRecipesQuery.execute(parameters);

        assertThat(recipes).isNotNull().hasSize(0);
    }

    @Test
    void testGetAllRecipesAfterAddinAnotherRecipeShouldReturnTwoItems() {
        List<RecipeIngredient> ingredients = Arrays.asList(
                RecipeIngredientMother.recipeIngredient()
                        .ingredientName("huevos")
                        .ingredientType(IngredientEnum.CARNES)
                        .weight(Mass.ofGrams(180))
                        .build(),
                RecipeIngredientMother.recipeIngredient()
                        .ingredientName("agua")
                        .ingredientType(IngredientEnum.ALIMENTOS_LIBRES_DE_ENERGIA)
                        .weight(Mass.ofGrams(500))
                        .build());

        List<RecipeInstruction> instructions = Arrays.asList(
                RecipeInstructionMother.recipeInstruction()
                        .instructionNumber(1)
                        .instruction("Hervir el agua y colocar los huevos durante 15 minutos")
                        .build(),
                RecipeInstructionMother.recipeInstruction()
                        .instructionNumber(2)
                        .instruction("Retirar los huevos y colocarlos en agua fria con hielos por 5 minutos")
                        .build());

        Recipe recipe = RecipeMother.recipe()
                .name("otros huevos hervidos")
                .cookTime(20)
                .ingredients(ingredients)
                .instructions(instructions)
                .build();

        recipeRepository.save(recipe);
        GetAllRecipesParameters parameters = new GetAllRecipesParameters();

        List<Recipe> recipes = getAllRecipesQuery.execute(parameters);

        assertThat(recipes).isNotNull().hasSize(2);
    }
}
