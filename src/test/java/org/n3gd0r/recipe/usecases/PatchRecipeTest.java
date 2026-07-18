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
import org.n3gd0r.recipe.domain.RecipeIngredientId;
import org.n3gd0r.recipe.domain.RecipeIngredientMother;
import org.n3gd0r.recipe.domain.RecipeInstruction;
import org.n3gd0r.recipe.domain.RecipeInstructionId;
import org.n3gd0r.recipe.domain.RecipeInstructionMother;
import org.n3gd0r.recipe.domain.RecipeMother;
import org.n3gd0r.recipe.repository.InMemoryRecipeRepository;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.n3gd0r.recipe.usecase.exception.NothingToPatchException;
import org.n3gd0r.recipe.usecase.patch.PatchIngredientParameters;
import org.n3gd0r.recipe.usecase.patch.PatchInstructionParameters;
import org.n3gd0r.recipe.usecase.patch.PatchRecipeCommand;
import org.n3gd0r.recipe.usecase.patch.PatchRecipeParameters;

public class PatchRecipeTest {
    private RecipeRepository recipeRepository;
    private PatchRecipeCommand patchRecipeCommand;
    private RecipeId recipeIdToPatch;
    private Recipe recipeToPatch;

    @BeforeEach
    void setUp() {
        recipeRepository = new InMemoryRecipeRepository();
        patchRecipeCommand = new PatchRecipeCommand(recipeRepository);

        recipeIdToPatch = new RecipeId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"));
        List<RecipeIngredient> ingredients = Arrays.asList(
                RecipeIngredientMother.recipeIngredient()
                        .id(new RecipeIngredientId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6")))
                        .ingredientName("huevos")
                        .ingredientType(IngredientEnum.CARNES)
                        .weight(Mass.ofGrams(180))
                        .build());
        List<RecipeInstruction> instructions = Arrays.asList(
                RecipeInstructionMother.recipeInstruction()
                        .id(new RecipeInstructionId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6")))
                        .instructionNumber(1)
                        .instruction("En agua hirviendo, colocar los huevos durante 15 minutos")
                        .build());
        recipeToPatch = RecipeMother.recipe()
                .id(recipeIdToPatch)
                .name("huevos cocidos")
                .cookTime(15)
                .ingredients(ingredients)
                .instructions(instructions)
                .build();

        recipeRepository.save(recipeToPatch);
    }

    @Test
    void testPatchRecipeName() {
        PatchRecipeParameters recipeParameters = new PatchRecipeParameters(recipeIdToPatch, "huevos hervidos", null,
                null, null);
        String recipeNameBeforePatch = recipeToPatch.getName();

        Recipe patchedRecipe = patchRecipeCommand.execute(recipeParameters);

        assertThat(patchedRecipe.getName()).isNotEqualToIgnoringCase(recipeNameBeforePatch);
        assertThat(patchedRecipe).isEqualTo(recipeToPatch);
    }

    @Test
    void testPatchRecipeWithEmptyNameThrowsException() {
        PatchRecipeParameters recipeParameters = new PatchRecipeParameters(recipeIdToPatch, "", null,
                null, null);
        assertThatExceptionOfType(NothingToPatchException.class)
                .isThrownBy(() -> patchRecipeCommand.execute(recipeParameters));
    }

    @Test
    void testPatchRecipeNameAndCooKTime() {
        PatchRecipeParameters recipeParameters = new PatchRecipeParameters(recipeIdToPatch, "huevos hervidos", 20,
                null, null);
        String recipeNameBeforePatch = recipeToPatch.getName();
        Integer cookTimeBeforePatch = recipeToPatch.getCookTime();

        Recipe patchedRecipe = patchRecipeCommand.execute(recipeParameters);

        assertThat(patchedRecipe.getName()).isNotEqualToIgnoringCase(recipeNameBeforePatch);
        assertThat(patchedRecipe.getCookTime()).isNotEqualTo(cookTimeBeforePatch);
        assertThat(patchedRecipe).isEqualTo(recipeToPatch);
    }

    @Test
    void testPatchRecipeIngredients() {
        RecipeIngredientId ingredienIdToPatch = new RecipeIngredientId(
                UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"));
        List<PatchIngredientParameters> ingredientParameters = Arrays.asList(
                new PatchIngredientParameters(ingredienIdToPatch, "Jamon", IngredientEnum.CARNES, Mass.ofGrams(100)));
        PatchRecipeParameters recipeParameters = new PatchRecipeParameters(recipeIdToPatch, null, null,
                ingredientParameters, null);
        int originalIngredientsSize = recipeToPatch.getIngredients().size();

        Recipe patchedRecipe = patchRecipeCommand.execute(recipeParameters);
        List<RecipeIngredient> patchedIngredients = patchedRecipe.getIngredients();

        assertThat(patchedIngredients.size()).isEqualTo(originalIngredientsSize);
        assertThat(patchedIngredients).hasSize(1);
        assertThat(patchedIngredients.getFirst().getIngredientName()).isEqualToIgnoringCase("jamon");
        assertThat(patchedIngredients.getFirst().getWeight().value()).isEqualTo(100);
    }

    @Test
    void testPatchRecipeInstruction() {
        RecipeInstructionId instructionIdToPatch = new RecipeInstructionId(
                UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"));
        List<PatchInstructionParameters> instructionParameters = Arrays.asList(
                new PatchInstructionParameters(instructionIdToPatch, 1,
                        "Colocar los huevos en agua hirviendo por 15 minutos"));
        PatchRecipeParameters recipeParameters = new PatchRecipeParameters(recipeIdToPatch, null, null, null,
                instructionParameters);
        int originalInstructionsSize = recipeToPatch.getInstructions().size();

        Recipe patchedRecipe = patchRecipeCommand.execute(recipeParameters);

        assertThat(patchedRecipe.getInstructions().size()).isEqualTo(originalInstructionsSize);
        assertThat(patchedRecipe.getInstructions()).hasSize(1);
        assertThat(patchedRecipe.getInstructions().getFirst().getInstruction())
                .isEqualToIgnoringCase("Colocar los huevos en agua hirviendo por 15 minutos");
    }

    @Test
    void testPatchRecipeAddIngredient() {
        List<PatchIngredientParameters> ingredientParameters = Arrays.asList(
                new PatchIngredientParameters(null, "Jamon", IngredientEnum.CARNES, Mass.ofGrams(100)));
        PatchRecipeParameters recipeParameters = new PatchRecipeParameters(recipeIdToPatch, null, null,
                ingredientParameters, null);
        int originalIngredientsSize = recipeToPatch.getIngredients().size();

        Recipe patchedRecipe = patchRecipeCommand.execute(recipeParameters);
        List<RecipeIngredient> patchedIngredients = patchedRecipe.getIngredients();

        assertThat(patchedIngredients.size()).isNotEqualTo(originalIngredientsSize);
        assertThat(patchedIngredients).hasSize(2);
        assertThat(patchedIngredients.get(0).getIngredientName()).isEqualToIgnoringCase("huevos");
        assertThat(patchedIngredients.get(0).getWeight().value()).isEqualTo(180);
        assertThat(patchedIngredients.get(1).getIngredientName()).isEqualToIgnoringCase("jamon");
        assertThat(patchedIngredients.get(1).getWeight().value()).isEqualTo(100);
    }

    @Test
    void testPatchRecipeAddInstruction() {
        List<PatchInstructionParameters> instructionParameters = Arrays.asList(
                new PatchInstructionParameters(null, 2, "Retirar los huevos y enfriarlos en agua con hielos"));
        PatchRecipeParameters recipeParameters = new PatchRecipeParameters(recipeIdToPatch, null, null, null,
                instructionParameters);
        int originalInstructionsSize = recipeToPatch.getInstructions().size();

        Recipe patchedRecipe = patchRecipeCommand.execute(recipeParameters);

        assertThat(patchedRecipe.getInstructions().size()).isNotEqualTo(originalInstructionsSize);
        assertThat(patchedRecipe.getInstructions()).hasSize(2);
        assertThat(patchedRecipe.getInstructions().get(1).getInstruction())
                .isEqualToIgnoringCase("Retirar los huevos y enfriarlos en agua con hielos");
    }

    @Test
    void testEmptyPatchRecipeThrowsException() {
        PatchRecipeParameters recipeParameters = new PatchRecipeParameters(recipeIdToPatch, null, null, null, null);

        assertThatExceptionOfType(NothingToPatchException.class)
                .isThrownBy(() -> patchRecipeCommand.execute(recipeParameters));
    }

    @Test
    void testPatchRecipeWithEmptyIngredientListThrowsException() {
        List<PatchIngredientParameters> ingredientParameters = Arrays.asList();
        PatchRecipeParameters recipeParameters = new PatchRecipeParameters(recipeIdToPatch, null, null,
                ingredientParameters, null);

        assertThatExceptionOfType(NothingToPatchException.class)
                .isThrownBy(() -> patchRecipeCommand.execute(recipeParameters));
    }

    @Test
    void testPatchRecipeWithInstructionListThrowsException() {
        List<PatchInstructionParameters> instructionParameters = Arrays.asList();
        PatchRecipeParameters recipeParameters = new PatchRecipeParameters(recipeIdToPatch, null, null,
                null, instructionParameters);

        assertThatExceptionOfType(NothingToPatchException.class)
                .isThrownBy(() -> patchRecipeCommand.execute(recipeParameters));
    }

    @Test
    void testPatchRecipeWithEmptyListsThrowsException() {
        List<PatchInstructionParameters> instructionParameters = Arrays.asList();
        List<PatchIngredientParameters> ingredientParameters = Arrays.asList();
        PatchRecipeParameters recipeParameters = new PatchRecipeParameters(recipeIdToPatch, null, null,
                ingredientParameters, instructionParameters);
        assertThatExceptionOfType(NothingToPatchException.class)
                .isThrownBy(() -> patchRecipeCommand.execute(recipeParameters));
    }
}
