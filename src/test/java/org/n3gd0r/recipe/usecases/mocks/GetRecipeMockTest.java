package org.n3gd0r.recipe.usecases.mocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.n3gd0r.recipe.usecases.mocks.MockTestUtils.recipeForMocks;
import static org.n3gd0r.recipe.usecases.mocks.MockTestUtils.recipeIdForMocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeId;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.n3gd0r.recipe.usecase.get.GetRecipeParameters;
import org.n3gd0r.recipe.usecase.get.GetRecipeQuery;

public class GetRecipeMockTest {
    private RecipeRepository repository;
    private GetRecipeQuery getRecipeQuery;

    @BeforeEach
    void setUp() {
        repository = mock(RecipeRepository.class);
        getRecipeQuery = new GetRecipeQuery(repository);
    }

    @Test
    void testMockRecipeRepositoryGetRecipeById() {
        Recipe mockedRecipe = recipeForMocks();
        RecipeId recipeId = recipeIdForMocks();
        when(repository.getById(any(RecipeId.class))).thenReturn(mockedRecipe);

        Recipe foundRecipe = getRecipeQuery.execute(new GetRecipeParameters(recipeId));

        verify(repository, times(1)).getById(any(RecipeId.class));
        assertEquals(mockedRecipe, foundRecipe);
    }

    @Test
    void testMockRecipeRepositoryGetRecipeByName() {
        Recipe mockedRecipe = recipeForMocks();
        when(repository.findByName(any(String.class))).thenReturn(mockedRecipe);

        Recipe foundRecipe = getRecipeQuery.execute(new GetRecipeParameters("huevos cocidos"));

        verify(repository, times(1)).findByName(any(String.class));
        assertEquals(mockedRecipe, foundRecipe);
    }
}
