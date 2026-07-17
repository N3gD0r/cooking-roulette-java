package org.n3gd0r.recipe.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

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
import org.n3gd0r.recipe.domain.RecipeIngredientMother;
import org.n3gd0r.recipe.domain.RecipeInstruction;
import org.n3gd0r.recipe.domain.RecipeInstructionMother;
import org.n3gd0r.recipe.domain.RecipeMother;
import org.n3gd0r.recipe.domain.exception.RecipeNotFoundException;
import org.n3gd0r.recipe.repository.InMemoryRecipeRepository;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.n3gd0r.recipe.usecase.delete.DeleteRecipeCommand;
import org.n3gd0r.recipe.usecase.delete.DeleteRecipeParameters;
import org.springframework.data.domain.PageRequest;

public class DeleteRecipeTest {
    private RecipeRepository recipeRepository;
    private DeleteRecipeCommand deleteRecipeCommand;
    private RecipeId recipeIdToDelete;

    @BeforeEach
    void setUp() {
        recipeRepository = new InMemoryRecipeRepository();
        deleteRecipeCommand = new DeleteRecipeCommand(recipeRepository);
        recipeIdToDelete = new RecipeId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"));

        List<RecipeIngredient> ingredients = Arrays.asList(
                RecipeIngredientMother.ingredient()
                        .ingredientName("huevos")
                        .ingredientType(IngredientEnum.CARNES)
                        .weight(Mass.ofGrams(180))
                        .build(),
                RecipeIngredientMother.ingredient()
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
                .id(recipeIdToDelete)
                .name("huevos cocidos")
                .cookTime(20)
                .ingredients(ingredients)
                .instructions(instructions)
                .build();

        recipeRepository.save(recipe);
    }

    @Test
    void testDeleteRecipeById() {
        DeleteRecipeParameters parameters = new DeleteRecipeParameters(recipeIdToDelete);

        boolean wasDeleted = deleteRecipeCommand.execute(parameters);
        List<Recipe> recipes = recipeRepository.findAll(PageRequest.of(0, 20)).toList();
        assertThat(wasDeleted).isTrue();
        assertThat(recipes).hasSize(0);
    }

    @Test
    void testDeleteRecipeWithNoRecipesAtAllThrowsException() {
        DeleteRecipeParameters parameters = new DeleteRecipeParameters(recipeIdToDelete);

        boolean wasDeleted = deleteRecipeCommand.execute(parameters);
        List<Recipe> recipes = recipeRepository.findAll(PageRequest.of(0, 20)).toList();

        assertThat(wasDeleted).isTrue();
        assertThat(recipes).hasSize(0);
        assertThatExceptionOfType(RecipeNotFoundException.class)
                .isThrownBy(() -> recipeRepository.validateExistsById(recipeIdToDelete));
    }
}
