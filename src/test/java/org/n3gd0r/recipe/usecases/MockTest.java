package org.n3gd0r.recipe.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Mass;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeId;
import org.n3gd0r.recipe.domain.RecipeIngredientMother;
import org.n3gd0r.recipe.domain.RecipeInstructionMother;
import org.n3gd0r.recipe.domain.RecipeMother;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.n3gd0r.recipe.usecase.get.GetRecipeParameters;
import org.n3gd0r.recipe.usecase.get.GetRecipeQuery;

public class MockTest {
    private RecipeRepository repository;
    private GetRecipeQuery getRecipeQuery;

    @BeforeEach
    void setUp() {
        repository = mock(RecipeRepository.class);
        getRecipeQuery = new GetRecipeQuery(repository);
    }

    @Test
    void testMockRecipeRepositoryGetRecipe() {
        Recipe mockedRecipe = recipeForMocks();
        RecipeId recipeId = recipeId();
        when(repository.getById(any(RecipeId.class))).thenReturn(mockedRecipe);

        Recipe foundRecipe = getRecipeQuery.execute(new GetRecipeParameters(recipeId));

        verify(repository, times(1)).getById(any(RecipeId.class));
        assertEquals(mockedRecipe, foundRecipe);
    }

    private static Recipe recipeForMocks() {
        return RecipeMother.recipe()
                .name("huevos cocidos")
                .cookTime(15)
                .withIngredient(RecipeIngredientMother.recipeIngredient()
                        .ingredientName("huevos")
                        .ingredientType(IngredientEnum.CARNES)
                        .weight(Mass.ofGrams(120))
                        .build())
                .withInstruction(RecipeInstructionMother.recipeInstruction()
                        .instruction("Hervir los huevos")
                        .instructionNumber(1)
                        .build())
                .build();
    }

    private static RecipeId recipeId() {
        return new RecipeId(UUID.randomUUID());
    }
}
