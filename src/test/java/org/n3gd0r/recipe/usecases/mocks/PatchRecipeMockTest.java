package org.n3gd0r.recipe.usecases.mocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.n3gd0r.recipe.domain.ParamsMother;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeMother;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.n3gd0r.recipe.usecase.patch.PatchRecipeHandler;
import org.n3gd0r.recipe.usecase.patch.PatchRecipeParameters;
import org.n3gd0r.recipe.usecases.TestUtils;

public class PatchRecipeMockTest {
    private RecipeRepository repository;
    private PatchRecipeHandler patchRecipeCommand;

    @BeforeEach
    void setUp() {
        repository = mock(RecipeRepository.class);
        patchRecipeCommand = new PatchRecipeHandler(repository);
    }

    @Test
    void testMockPatchRecipe() {
        UUID recipeId = TestUtils.recipeIdGenerator();
        PatchRecipeParameters patchParameters = ParamsMother.PatchRecipeParamsMother.patchRecipeParamsBuilder()
                .id(recipeId)
                .name("huevos mock")
                .cookTime(15)
                .build();
        Recipe recipe = RecipeMother.recipe().build();

        doNothing().when(repository).validateExistsById(recipeId);
        doNothing().when(repository).save(recipe);
        when(repository.getById(recipeId)).thenReturn(recipe);
        Recipe savedRecipe = patchRecipeCommand.execute(patchParameters);

        verify(repository, times(1)).validateExistsById(recipeId);
        verify(repository, times(1)).save(recipe);
        verify(repository, times(1)).getById(recipeId);
        assertNotNull(savedRecipe);
        assertEquals(recipe, savedRecipe);
    }
}
