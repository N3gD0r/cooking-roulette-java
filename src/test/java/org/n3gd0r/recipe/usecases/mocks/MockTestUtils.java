package org.n3gd0r.recipe.usecases.mocks;

import java.util.UUID;

import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Mass;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeIngredientMother;
import org.n3gd0r.recipe.domain.RecipeInstructionMother;
import org.n3gd0r.recipe.domain.RecipeMother;

public class MockTestUtils {
    public static Recipe recipeForMocks() {
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

    public static UUID recipeIdForMocks() {
        return UUID.randomUUID();
    }

    public static UUID recipeIngredientIdForMocks() {
        return UUID.randomUUID();
    }

    public static UUID recipeInstructionIdForMocks() {
        return UUID.randomUUID();
    }
}
