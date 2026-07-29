package org.n3gd0r.recipe.usecases.mocks;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.n3gd0r.recipe.usecase.update.UpdateRecipeCommand;

public class UpdateRecipeMockTest {
    private RecipeRepository repository;
    private UpdateRecipeCommand updateRecipeCommand;

    @BeforeEach
    void setUp() {
        repository = mock(repository);
        updateRecipeCommand = new UpdateRecipeCommand(repository);
    }

    @Test
    void testMockUpdateRecipe() {
    }
}
