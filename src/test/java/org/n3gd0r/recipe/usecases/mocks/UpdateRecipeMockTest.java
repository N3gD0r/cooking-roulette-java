package org.n3gd0r.recipe.usecases.mocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.n3gd0r.recipe.usecases.TestUtils.ingredientIdGenerator;
import static org.n3gd0r.recipe.usecases.TestUtils.instructionIdGenerator;
import static org.n3gd0r.recipe.usecases.TestUtils.recipeForMocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.n3gd0r.recipe.usecase.update.UpdateIngredientParameters;
import org.n3gd0r.recipe.usecase.update.UpdateInstructionParameters;
import org.n3gd0r.recipe.usecase.update.UpdateRecipeHandler;
import org.n3gd0r.recipe.usecase.update.UpdateRecipeParameters;

public class UpdateRecipeMockTest {
    private RecipeRepository repository;
    private UpdateRecipeHandler updateRecipeCommand;

    @BeforeEach
    void setUp() {
        repository = mock(RecipeRepository.class);
        updateRecipeCommand = new UpdateRecipeHandler(repository);
    }

    @Test
    void testMockUpdateRecipe() {
        Recipe recipe = recipeForMocks();
        UpdateRecipeParameters parameters = new UpdateRecipeParameters(recipe.getId(),
                "Another recipe name",
                75,
                recipe.getIngredients().stream()
                        .map(ri -> new UpdateIngredientParameters(ri.getIngredientName(),
                                ri.getIngredientType(),
                                ri.getWeight()))
                        .toList(),
                recipe.getInstructions().stream()
                        .map(ri -> new UpdateInstructionParameters(ri.getInstructionNumber(),
                                ri.getInstruction()))
                        .toList());

        doNothing().when(repository).validateExistsById(recipe.getId());
        when(repository.getById(recipe.getId())).thenReturn(recipe);
        doNothing().when(repository).validateNameUnique(any(String.class));
        when(repository.nextRecipeIngredientId()).thenReturn(ingredientIdGenerator());
        when(repository.nextRecipeInstructionId()).thenReturn(instructionIdGenerator());
        doNothing().when(repository).save(recipe);
        Recipe updatedRecipe = updateRecipeCommand.execute(parameters);

        verify(repository, times(1)).validateExistsById(recipe.getId());
        verify(repository, times(1)).getById(recipe.getId());
        verify(repository, times(1)).validateNameUnique(any(String.class));
        verify(repository, times(1)).nextRecipeIngredientId();
        verify(repository, times(1)).nextRecipeInstructionId();
        assertNotNull(updatedRecipe);
        assertEquals(recipe, updatedRecipe);
    }
}
