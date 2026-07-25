package org.n3gd0r.recipe.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        assertNotEquals(recipeNameBeforePatch, patchedRecipe.getName());
        assertEquals(patchedRecipe, recipeToPatch);
    }

    @Test
    void testPatchRecipeWithEmptyNameThrowsException() {
        PatchRecipeParameters recipeParameters = new PatchRecipeParameters(recipeIdToPatch, "", null,
                null, null);

        assertThrows(NothingToPatchException.class, () -> patchRecipeCommand.execute(recipeParameters));
    }

    @Test
    void testPatchRecipeNameAndCooKTime() {
        PatchRecipeParameters recipeParameters = new PatchRecipeParameters(recipeIdToPatch, "huevos hervidos", 20,
                null, null);
        String recipeNameBeforePatch = recipeToPatch.getName();
        Integer cookTimeBeforePatch = recipeToPatch.getCookTime();

        Recipe patchedRecipe = patchRecipeCommand.execute(recipeParameters);

        assertNotEquals(recipeNameBeforePatch, patchedRecipe.getName());
        assertNotEquals(cookTimeBeforePatch, patchedRecipe.getCookTime());
        assertEquals(recipeToPatch, patchedRecipe);
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

        assertTrue(patchedIngredients.size() == originalIngredientsSize);
        assertTrue(patchedIngredients.size() == 1);
        assertEquals("jamon", patchedIngredients.getFirst().getIngredientName());
        assertEquals(100, patchedIngredients.getFirst().getWeight().value());
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

        assertTrue(originalInstructionsSize == patchedRecipe.getInstructions().size());
        assertTrue(patchedRecipe.getInstructions().size() == 1);
        assertEquals("Colocar los huevos en agua hirviendo por 15 minutos",
                patchedRecipe.getInstructions().getFirst().getInstruction());
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

        assertThat(patchedIngredients.get(1).getIngredientName()).isEqualToIgnoringCase("jamon");
        assertThat(patchedIngredients.get(1).getWeight().value()).isEqualTo(100);

        assertNotEquals(originalIngredientsSize, patchedIngredients.size());
        assertTrue(patchedIngredients.size() == 2);
        assertEquals("huevos", patchedIngredients.get(0).getIngredientName());
        assertEquals(180, patchedIngredients.get(0).getWeight().value());
        assertEquals("jamon", patchedIngredients.get(1).getIngredientName());
        assertEquals(100, patchedIngredients.get(1).getWeight().value());
    }

    @Test
    void testPatchRecipeAddInstruction() {
        List<PatchInstructionParameters> instructionParameters = Arrays.asList(
                new PatchInstructionParameters(null, 2, "Retirar los huevos y enfriarlos en agua con hielos"));
        PatchRecipeParameters recipeParameters = new PatchRecipeParameters(recipeIdToPatch, null, null, null,
                instructionParameters);
        int originalInstructionsSize = recipeToPatch.getInstructions().size();

        Recipe patchedRecipe = patchRecipeCommand.execute(recipeParameters);

        assertTrue(originalInstructionsSize != patchedRecipe.getInstructions().size());
        assertTrue(patchedRecipe.getInstructions().size() == 2);
        assertEquals("Retirar los huevos y enfriarlos en agua con hielos",
                patchedRecipe.getInstructions().get(1).getInstruction());
    }

    @Test
    void testEmptyPatchRecipeThrowsException() {
        PatchRecipeParameters recipeParameters = new PatchRecipeParameters(recipeIdToPatch, null, null, null, null);

        assertThrows(NothingToPatchException.class, () -> patchRecipeCommand.execute(recipeParameters));
    }

    @Test
    void testPatchRecipeWithEmptyIngredientListThrowsException() {
        List<PatchIngredientParameters> ingredientParameters = Arrays.asList();
        PatchRecipeParameters recipeParameters = new PatchRecipeParameters(recipeIdToPatch, null, null,
                ingredientParameters, null);

        assertThrows(NothingToPatchException.class, () -> patchRecipeCommand.execute(recipeParameters));
    }

    @Test
    void testPatchRecipeWithInstructionListThrowsException() {
        List<PatchInstructionParameters> instructionParameters = Arrays.asList();
        PatchRecipeParameters recipeParameters = new PatchRecipeParameters(recipeIdToPatch, null, null,
                null, instructionParameters);

        assertThrows(NothingToPatchException.class, () -> patchRecipeCommand.execute(recipeParameters));
    }

    @Test
    void testPatchRecipeWithEmptyListsThrowsException() {
        List<PatchInstructionParameters> instructionParameters = Arrays.asList();
        List<PatchIngredientParameters> ingredientParameters = Arrays.asList();
        PatchRecipeParameters recipeParameters = new PatchRecipeParameters(recipeIdToPatch, null, null,
                ingredientParameters, instructionParameters);
        assertThrows(NothingToPatchException.class, () -> patchRecipeCommand.execute(recipeParameters));
    }
}
