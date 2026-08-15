package org.n3gd0r.recipe.usecases.mocks;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.n3gd0r.recipe.domain.RecipeId;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.n3gd0r.recipe.usecase.delete.DeleteRecipeHandler;
import org.n3gd0r.recipe.usecase.delete.DeleteRecipeParameters;

public class DeleteRecipeMockTest {
    private DeleteRecipeHandler deleteRecipeCommand;
    private RecipeRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(RecipeRepository.class);
        deleteRecipeCommand = new DeleteRecipeHandler(repository);
    }

    @Test
    void testMockDeleteRecipe() {
        RecipeId recipeId = new RecipeId(UUID.randomUUID());
        doNothing().when(repository).deleteById(recipeId);

        deleteRecipeCommand.execute(new DeleteRecipeParameters(recipeId));

        verify(repository, times(1)).deleteById(recipeId);
    }
}
