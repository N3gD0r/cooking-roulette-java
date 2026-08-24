package org.n3gd0r.recipe.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.n3gd0r.recipe.domain.ParamsMother.PatchIngredientsParamsMother.patchIngredientParamsBuilder;
import static org.n3gd0r.recipe.domain.ParamsMother.PatchInstructionParamsMother.patchInstructionParamsBuilder;
import static org.n3gd0r.recipe.domain.ParamsMother.PatchRecipeParamsMother.patchRecipeParamsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Mass;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeIngredient;
import org.n3gd0r.recipe.domain.RecipeIngredientMother;
import org.n3gd0r.recipe.domain.RecipeInstruction;
import org.n3gd0r.recipe.domain.RecipeInstructionMother;
import org.n3gd0r.recipe.domain.RecipeMother;
import org.n3gd0r.recipe.repository.InMemoryRecipeRepository;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.n3gd0r.recipe.usecase.exception.NothingToPatchException;
import org.n3gd0r.recipe.usecase.patch.PatchIngredientParameters;
import org.n3gd0r.recipe.usecase.patch.PatchInstructionParameters;
import org.n3gd0r.recipe.usecase.patch.PatchRecipeHandler;
import org.n3gd0r.recipe.usecase.patch.PatchRecipeParameters;

public class PatchRecipeTest {
    private RecipeRepository recipeRepository;
    private PatchRecipeHandler patchRecipeCommand;
    private UUID recipeIdToPatch;
    private UUID ingredientIdToPatch;
    private UUID instructionIdToPatch;
    private Recipe recipeToPatch;

    @BeforeEach
    void setUp() {
        recipeRepository = new InMemoryRecipeRepository();
        patchRecipeCommand = new PatchRecipeHandler(recipeRepository);

        recipeIdToPatch = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
        ingredientIdToPatch = TestUtils.ingredientIdGenerator();
        instructionIdToPatch = TestUtils.ingredientIdGenerator();
        List<RecipeIngredient> ingredients = Arrays.asList(
                RecipeIngredientMother.recipeIngredient()
                        .id(ingredientIdToPatch)
                        .ingredientName("huevos")
                        .ingredientType(IngredientEnum.CARNES)
                        .weight(Mass.ofGrams(180))
                        .build());
        List<RecipeInstruction> instructions = Arrays.asList(
                RecipeInstructionMother.recipeInstruction()
                        .id(instructionIdToPatch)
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
        PatchRecipeParameters recipeParameters = patchRecipeParamsBuilder()
                .id(recipeIdToPatch)
                .name("huevos hervidos")
                .build();
        String recipeNameBeforePatch = recipeToPatch.getName();

        Recipe patchedRecipe = patchRecipeCommand.execute(recipeParameters);

        assertNotEquals(recipeNameBeforePatch, patchedRecipe.getName());
        assertEquals(patchedRecipe, recipeToPatch);
    }

    @Test
    void testPatchRecipeWithEmptyNameThrowsException() {
        PatchRecipeParameters recipeParameters = patchRecipeParamsBuilder()
                .id(recipeIdToPatch)
                .name("")
                .build();

        assertThrows(NothingToPatchException.class, () -> patchRecipeCommand.execute(recipeParameters));
    }

    @Test
    void testPatchRecipeNameAndCooKTime() {
        PatchRecipeParameters recipeParameters = patchRecipeParamsBuilder()
                .id(recipeIdToPatch)
                .name("huevos hervidos")
                .cookTime(20)
                .build();
        String recipeNameBeforePatch = recipeToPatch.getName();
        Integer cookTimeBeforePatch = recipeToPatch.getCookTime();

        Recipe patchedRecipe = patchRecipeCommand.execute(recipeParameters);

        assertNotEquals(recipeNameBeforePatch, patchedRecipe.getName());
        assertNotEquals(cookTimeBeforePatch, patchedRecipe.getCookTime());
        assertEquals(recipeToPatch, patchedRecipe);
    }

    @Test
    void testPatchRecipeIngredients() {
        List<PatchIngredientParameters> ingredientParameters = Arrays.asList(patchIngredientParamsBuilder()
                .id(ingredientIdToPatch)
                .ingredientName("Jamon")
                .ingredientType(IngredientEnum.CARNES)
                .weight(100)
                .build());

        PatchRecipeParameters recipeParameters = patchRecipeParamsBuilder()
                .id(recipeIdToPatch)
                .ingredients(ingredientParameters)
                .build();
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
        List<PatchInstructionParameters> instructionParameters = Arrays.asList(patchInstructionParamsBuilder()
                .id(instructionIdToPatch)
                .instructionNumber(1)
                .instruction("Colocar los huevos en agua hirviendo por 15 minutos")
                .build());
        PatchRecipeParameters recipeParameters = patchRecipeParamsBuilder()
                .id(recipeIdToPatch).instructions(instructionParameters).build();
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
                patchIngredientParamsBuilder()
                        .ingredientName("Jamon")
                        .ingredientType(IngredientEnum.CARNES)
                        .weight(100)
                        .build());

        PatchRecipeParameters recipeParameters = patchRecipeParamsBuilder()
                .id(recipeIdToPatch)
                .ingredients(ingredientParameters)
                .build();

        int originalIngredientsSize = recipeToPatch.getIngredients().size();

        Recipe patchedRecipe = patchRecipeCommand.execute(recipeParameters);
        List<RecipeIngredient> patchedIngredients = patchedRecipe.getIngredients();

        assertEquals(patchedIngredients.get(1).getIngredientName(), "jamon");
        assertEquals(patchedIngredients.get(1).getWeight().value(), 100);
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
                patchInstructionParamsBuilder()
                        .instructionNumber(2)
                        .instruction("Retirar los huevos y enfriarlos en agua con hielos")
                        .build());

        PatchRecipeParameters recipeParameters = patchRecipeParamsBuilder()
                .id(recipeIdToPatch)
                .instructions(instructionParameters)
                .build();

        int originalInstructionsSize = recipeToPatch.getInstructions().size();

        Recipe patchedRecipe = patchRecipeCommand.execute(recipeParameters);

        assertTrue(originalInstructionsSize != patchedRecipe.getInstructions().size());
        assertTrue(patchedRecipe.getInstructions().size() == 2);
        assertEquals("Retirar los huevos y enfriarlos en agua con hielos",
                patchedRecipe.getInstructions().get(1).getInstruction());
    }

    @Test
    void testEmptyPatchRecipeThrowsException() {
        PatchRecipeParameters recipeParameters = patchRecipeParamsBuilder().id(recipeIdToPatch).build();

        assertThrows(NothingToPatchException.class, () -> patchRecipeCommand.execute(recipeParameters));
    }

    @Test
    void testPatchRecipeWithEmptyIngredientListThrowsException() {
        List<PatchIngredientParameters> ingredientParameters = new ArrayList<>();

        PatchRecipeParameters recipeParameters = patchRecipeParamsBuilder()
                .id(recipeIdToPatch)
                .ingredients(ingredientParameters)
                .build();

        assertThrows(NothingToPatchException.class, () -> patchRecipeCommand.execute(recipeParameters));
    }

    @Test
    void testPatchRecipeWithInstructionListThrowsException() {
        List<PatchInstructionParameters> instructionParameters = new ArrayList<>();

        PatchRecipeParameters recipeParameters = patchRecipeParamsBuilder()
                .id(recipeIdToPatch)
                .instructions(instructionParameters)
                .build();

        assertThrows(NothingToPatchException.class, () -> patchRecipeCommand.execute(recipeParameters));
    }

    @Test
    void testPatchRecipeWithEmptyListsThrowsException() {
        List<PatchInstructionParameters> instructionParameters = new ArrayList<>();
        List<PatchIngredientParameters> ingredientParameters = new ArrayList<>();

        PatchRecipeParameters recipeParameters = patchRecipeParamsBuilder()
                .id(recipeIdToPatch)
                .instructions(instructionParameters)
                .ingredients(ingredientParameters)
                .build();

        assertThrows(NothingToPatchException.class, () -> patchRecipeCommand.execute(recipeParameters));
    }
}
