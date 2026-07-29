package org.n3gd0r.recipe.usecases.mocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeIngredient;
import org.n3gd0r.recipe.domain.RecipeIngredientId;
import org.n3gd0r.recipe.domain.RecipeIngredientMother;
import org.n3gd0r.recipe.domain.RecipeInstruction;
import org.n3gd0r.recipe.domain.RecipeInstructionId;
import org.n3gd0r.recipe.domain.RecipeInstructionMother;
import org.n3gd0r.recipe.domain.RecipeMother;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.n3gd0r.recipe.usecase.register.RegisterIngredientParameters;
import org.n3gd0r.recipe.usecase.register.RegisterInstructionParameters;
import org.n3gd0r.recipe.usecase.register.RegisterRecipeCommand;
import org.n3gd0r.recipe.usecase.register.RegisterRecipeParameters;

public class RegisterRecipeMockTest {
    private RecipeRepository repository;
    private RegisterRecipeCommand registerRecipeCommand;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(RecipeRepository.class);
        registerRecipeCommand = new RegisterRecipeCommand(repository);
    }

    @Test
    void testMockRegisterRecipe() {
        Recipe recipeToRegister = RecipeMother.recipe()
                .withIngredient(RecipeIngredientMother.recipeIngredient().build())
                .withInstruction(RecipeInstructionMother.recipeInstruction().build())
                .build();
        RegisterRecipeParameters parameters = new RegisterRecipeParameters(recipeToRegister.getName(),
                recipeToRegister.getCookTime(),
                toIngredientParameters(recipeToRegister.getIngredients()),
                toInstructionParameters(recipeToRegister.getInstructions()));

        doNothing().when(repository).validateNameUnique(any(String.class));
        when(repository.nextRecipeIngredientId()).thenReturn(new RecipeIngredientId(UUID.randomUUID()));
        when(repository.nextRecipeInstructionId()).thenReturn(new RecipeInstructionId(UUID.randomUUID()));
        when(repository.nextId()).thenReturn(recipeToRegister.getId());
        doNothing().when(repository).save(recipeToRegister);
        Recipe registeredRecipe = registerRecipeCommand.execute(parameters);

        verify(repository, times(1)).validateNameUnique(any(String.class));
        verify(repository, times(1)).nextRecipeIngredientId();
        verify(repository, times(1)).nextRecipeInstructionId();
        verify(repository, times(1)).nextId();
        verify(repository, times(1)).save(recipeToRegister);
        assertNotNull(registeredRecipe);
        assertEquals(recipeToRegister, registeredRecipe);
    }

    private List<RegisterIngredientParameters> toIngredientParameters(List<RecipeIngredient> ingredients) {
        return ingredients.stream().map(
                ri -> new RegisterIngredientParameters(ri.getIngredientName(), ri.getIngredientType(), ri.getWeight()))
                .toList();
    }

    private List<RegisterInstructionParameters> toInstructionParameters(List<RecipeInstruction> instructions) {
        return instructions.stream()
                .map(ri -> new RegisterInstructionParameters(ri.getInstructionNumber(), ri.getInstruction())).toList();
    }
}
