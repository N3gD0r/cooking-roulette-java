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
import org.n3gd0r.recipe.domain.exception.RecipeWithNameAlreadyExistsException;
import org.n3gd0r.recipe.repository.InMemoryRecipeRepository;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.n3gd0r.recipe.usecase.update.UpdateIngredientParameters;
import org.n3gd0r.recipe.usecase.update.UpdateInstructionParameters;
import org.n3gd0r.recipe.usecase.update.UpdateRecipeCommand;
import org.n3gd0r.recipe.usecase.update.UpdateRecipeParameters;

public class UpdateRecipeTest {
    private RecipeRepository recipeRepository;
    private UpdateRecipeCommand updateRecipeCommand;
    private RecipeId recipeIdToUpdate;

    @BeforeEach
    void setUp() {
        recipeRepository = new InMemoryRecipeRepository();
        updateRecipeCommand = new UpdateRecipeCommand(recipeRepository);

        recipeIdToUpdate = new RecipeId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"));
        List<RecipeIngredient> ingredients = Arrays.asList(
                RecipeIngredientMother.ingredient()
                        .ingredientName("huevos")
                        .ingredientType(IngredientEnum.CARNES)
                        .weight(Mass.ofGrams(180))
                        .build());
        List<RecipeInstruction> instructions = Arrays.asList(
                RecipeInstructionMother.recipeInstruction()
                        .instructionNumber(1)
                        .instruction("En agua hirviendo, colocar los huevos durante 15 minutos")
                        .build());
        Recipe recipe = RecipeMother.recipe()
                .id(recipeIdToUpdate)
                .name("huevos cocidos")
                .cookTime(15)
                .ingredients(ingredients)
                .instructions(instructions)
                .build();

        recipeRepository.save(recipe);
    }

    @Test
    void testUpdateRecipe() {
        List<UpdateIngredientParameters> ingredientParameters = Arrays.asList(
                new UpdateIngredientParameters("huevos",
                        IngredientEnum.CARNES,
                        Mass.ofGrams(140)),
                new UpdateIngredientParameters("agua",
                        IngredientEnum.ALIMENTOS_LIBRES_DE_ENERGIA,
                        Mass.ofGrams(500)));
        List<UpdateInstructionParameters> instructionParameters = Arrays.asList(
                new UpdateInstructionParameters(1,
                        "Hervir el agua en un posillo donde quepan los huevos"),
                new UpdateInstructionParameters(2,
                        "Colocar los huevos en el posillo con el agua hirviendo durante 15 minutos"),
                new UpdateInstructionParameters(3,
                        "Retirar los huevos y dejarlos enfriar en agua fria con hielos durante 5 minutos"));
        UpdateRecipeParameters recipeParameters = new UpdateRecipeParameters(recipeIdToUpdate, "huevos hervidos", 25,
                ingredientParameters,
                instructionParameters);

        Recipe updatedRecipe = updateRecipeCommand.execute(recipeParameters);

        assertThat(updatedRecipe).isNotNull();
        assertThat(updatedRecipe.getIngredients()).hasSize(2);
        assertThat(updatedRecipe.getInstructions()).hasSize(3);
    }

    @Test
    void testUpdateRecipeWithSameNameShouldWork() {
        List<UpdateIngredientParameters> ingredients = Arrays.asList(
                new UpdateIngredientParameters("huevos",
                        IngredientEnum.CARNES,
                        Mass.ofGrams(140)),
                new UpdateIngredientParameters("agua",
                        IngredientEnum.ALIMENTOS_LIBRES_DE_ENERGIA,
                        Mass.ofGrams(500)));
        List<UpdateInstructionParameters> instructions = Arrays.asList(
                new UpdateInstructionParameters(1,
                        "Hervir el agua en un posillo donde quepan los huevos"),
                new UpdateInstructionParameters(2,
                        "Colocar los huevos en el posillo con el agua hirviendo durante 15 minutos"),
                new UpdateInstructionParameters(3,
                        "Retirar los huevos y dejarlos enfriar en agua fria con hielos durante 5 minutos"));
        UpdateRecipeParameters parameters = new UpdateRecipeParameters(recipeIdToUpdate, "huevos cocidos", 25,
                ingredients,
                instructions);

        Recipe updatedRecipe = updateRecipeCommand.execute(parameters);

        assertThat(updatedRecipe).isNotNull();
        assertThat(updatedRecipe.getIngredients()).hasSize(2);
        assertThat(updatedRecipe.getInstructions()).hasSize(3);
    }

    @Test
    void testUpdateRecipeWithIdNotInDatabaseThrowsException() {
        List<UpdateIngredientParameters> ingredients = Arrays.asList(
                new UpdateIngredientParameters("huevos",
                        IngredientEnum.CARNES,
                        Mass.ofGrams(140)),
                new UpdateIngredientParameters("agua",
                        IngredientEnum.ALIMENTOS_LIBRES_DE_ENERGIA,
                        Mass.ofGrams(500)));
        List<UpdateInstructionParameters> instructions = Arrays.asList(
                new UpdateInstructionParameters(1,
                        "Hervir el agua en un posillo donde quepan los huevos"),
                new UpdateInstructionParameters(2,
                        "Colocar los huevos en el posillo con el agua hirviendo durante 15 minutos"),
                new UpdateInstructionParameters(3,
                        "Retirar los huevos y dejarlos enfriar en agua fria con hielos durante 5 minutos"));
        UpdateRecipeParameters parameters = new UpdateRecipeParameters(new RecipeId(UUID.randomUUID()),
                "huevos cocidos", 25, ingredients,
                instructions);

        assertThatExceptionOfType(RecipeNotFoundException.class)
                .isThrownBy(() -> updateRecipeCommand.execute(parameters));
    }

    @Test
    void testUpdateRecipeWithAnotherRecipeNameThrowsException() {
        List<RecipeIngredient> recipeIngredients = Arrays.asList(
                RecipeIngredientMother.ingredient()
                        .ingredientName("huevos")
                        .ingredientType(IngredientEnum.CARNES)
                        .weight(Mass.ofGrams(180))
                        .build());
        List<RecipeInstruction> recipeInstructions = Arrays.asList(
                RecipeInstructionMother.recipeInstruction()
                        .instructionNumber(1)
                        .instruction("En agua hirviendo, colocar los huevos durante 15 minutos")
                        .build());
        Recipe recipe = RecipeMother.recipe()
                .name("huevos hervidos")
                .cookTime(15)
                .ingredients(recipeIngredients)
                .instructions(recipeInstructions)
                .build();
        recipeRepository.save(recipe);

        List<UpdateIngredientParameters> ingredientParameters = Arrays.asList(
                new UpdateIngredientParameters("huevos",
                        IngredientEnum.CARNES,
                        Mass.ofGrams(140)),
                new UpdateIngredientParameters("agua",
                        IngredientEnum.ALIMENTOS_LIBRES_DE_ENERGIA,
                        Mass.ofGrams(500)));
        List<UpdateInstructionParameters> instructionParameters = Arrays.asList(
                new UpdateInstructionParameters(1,
                        "Hervir el agua en un posillo donde quepan los huevos"),
                new UpdateInstructionParameters(2,
                        "Colocar los huevos en el posillo con el agua hirviendo durante 15 minutos"),
                new UpdateInstructionParameters(3,
                        "Retirar los huevos y dejarlos enfriar en agua fria con hielos durante 5 minutos"));
        UpdateRecipeParameters recipeParameters = new UpdateRecipeParameters(recipeIdToUpdate, "huevos hervidos", 25,
                ingredientParameters,
                instructionParameters);

        assertThatExceptionOfType(RecipeWithNameAlreadyExistsException.class)
                .isThrownBy(() -> updateRecipeCommand.execute(recipeParameters));
    }
}
