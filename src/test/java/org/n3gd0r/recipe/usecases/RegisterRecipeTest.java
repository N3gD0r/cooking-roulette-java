package org.n3gd0r.recipe.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Mass;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.exception.RecipeWithNameAlreadyExistsException;
import org.n3gd0r.recipe.repository.InMemoryRecipeRepository;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.n3gd0r.recipe.usecase.register.RegisterIngredientParameters;
import org.n3gd0r.recipe.usecase.register.RegisterInstructionParameters;
import org.n3gd0r.recipe.usecase.register.RegisterRecipeCommand;
import org.n3gd0r.recipe.usecase.register.RegisterRecipeParameters;
import org.springframework.data.domain.PageRequest;

public class RegisterRecipeTest {
    private RecipeRepository recipeRepository;
    private RegisterRecipeCommand registerRecipeCommand;

    @BeforeEach
    void setUp() {
        recipeRepository = new InMemoryRecipeRepository();
        registerRecipeCommand = new RegisterRecipeCommand(recipeRepository);
    }

    @Test
    void testRegisterOneRecipe() {
        List<RegisterIngredientParameters> ingredients = Arrays.asList(
                new RegisterIngredientParameters("huevos", IngredientEnum.CARNES, Mass.ofGrams(180)));
        List<RegisterInstructionParameters> instructions = Arrays.asList(
                new RegisterInstructionParameters(1, "En agua hirviendo, colocar los huevos durante 15 minutos."),
                new RegisterInstructionParameters(2,
                        "Despues de ese lapso de tiempo, retirar los huevos y colocarlos en agua fria durante 5 minutos"),
                new RegisterInstructionParameters(3, "Pelar los huevos"));
        RegisterRecipeParameters recipeParameters = new RegisterRecipeParameters("huevos cocidos", 15, ingredients,
                instructions);
        Recipe recipe = registerRecipeCommand.execute(recipeParameters);

        assertThat(recipe).isNotNull();
        assertThat(recipeRepository.findAll(PageRequest.of(0, 10))).hasSize(1);
    }

    @Test
    void testRegisterRecipeWithSameNameThrowsException() {
        List<RegisterIngredientParameters> ingredients = Arrays.asList(
                new RegisterIngredientParameters("huevos", IngredientEnum.CARNES, Mass.ofGrams(180)));
        List<RegisterInstructionParameters> instructions = Arrays.asList(
                new RegisterInstructionParameters(1, "En agua hirviendo, colocar los huevos durante 15 minutos."),
                new RegisterInstructionParameters(2,
                        "Despues de ese lapso de tiempo, retirar los huevos y colocarlos en agua fria durante 5 minutos"),
                new RegisterInstructionParameters(3, "Pelar los huevos"));
        RegisterRecipeParameters recipeParameters = new RegisterRecipeParameters("huevos cocidos", 15, ingredients,
                instructions);
        registerRecipeCommand.execute(recipeParameters);

        assertThatExceptionOfType(RecipeWithNameAlreadyExistsException.class)
                .isThrownBy(() -> registerRecipeCommand.execute(recipeParameters));
    }

    @Test
    void testRegisterRecipeWithEmptyIngredientsThrowsException() {
        List<RegisterIngredientParameters> ingredients = Arrays.asList();
        List<RegisterInstructionParameters> instructions = Arrays.asList(
                new RegisterInstructionParameters(1, "En agua hirviendo, colocar los huevos durante 15 minutos."),
                new RegisterInstructionParameters(2,
                        "Despues de ese lapso de tiempo, retirar los huevos y colocarlos en agua fria durante 5 minutos"),
                new RegisterInstructionParameters(3, "Pelar los huevos"));

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new RegisterRecipeParameters("huevos cocidos", 15, ingredients, instructions));
    }

    @Test
    void testRegisterRecipeWithEmptyInstructionsThrowsException() {
        List<RegisterIngredientParameters> ingredients = Arrays.asList(
                new RegisterIngredientParameters("huevos", IngredientEnum.CARNES, Mass.ofGrams(180)));
        List<RegisterInstructionParameters> instructions = Arrays.asList();

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new RegisterRecipeParameters("huevos cocidos", 15, ingredients, instructions));
    }
}
