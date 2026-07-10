package org.n3gd0r.recipe.usecase;

import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Mass;
import org.springframework.util.Assert;

/**
 * RegisterRecipeWithIngredientParameters
 */
public record RegisterRecipeWithAllParameters(RegisterRecipeWithParameters recipeParameters,
        RegisterRecipeWithIngredientsParameters ingredientParameters,
        RegisterRecipeWithInstructionsParameters instructionParameters) {
    public RegisterRecipeWithAllParameters {
        Assert.notNull(recipeParameters, "The recipeParameters should not be null");
        Assert.notNull(ingredientParameters, "The ingredientParameters should not be null");
        Assert.notNull(instructionParameters, "The instructionParameters should not be null");
    }

    public record RegisterRecipeWithParameters(String name, float cookTime) {
        public RegisterRecipeWithParameters {
            Assert.hasText(name, "The RegisterRecipeWithParameters name should have text");
            Assert.notNull(name, "The RegisterRecipeWithParameters name should not be null");
            Assert.isTrue(cookTime > 0.0, "The RegisterRecipeWithParameters cookTime should be greater than 0");
            Assert.notNull(cookTime, "The RegisterRecipeWithParameters cookTime should not be null");
        }
    }

    public record RegisterRecipeWithIngredientsParameters(String ingredientName, IngredientEnum ingredientType,
            Mass weight) {
        public RegisterRecipeWithIngredientsParameters {
            Assert.notNull(ingredientName,
                    "The RegisterRecipeWithIngredientsParameters ingredientName should not be null");
            Assert.notNull(ingredientType,
                    "The RegisterRecipeWithIngredientsParameters ingredientType should not be null");
            Assert.notNull(weight,
                    "The RegisterRecipeWithIngredientsParameters weight should not be null");
        }
    }

    public record RegisterRecipeWithInstructionsParameters(int stepNumber, String stepInstruction) {
        public RegisterRecipeWithInstructionsParameters {
            Assert.isTrue(stepNumber > 0,
                    "The RegisterRecipeWithInstructionsParameters stepNumber should not be less de 1");
            Assert.notNull(stepNumber, "The RegisterRecipeWithInstructionsParameters stepNumber should not be null");
            Assert.hasText(stepInstruction,
                    "The RegisterRecipeWithInstructionsParameters stepInstruction should have text");
            Assert.notNull(stepInstruction,
                    "The RegisterRecipeWithInstructionsParameters stepInstruction should not be null");
        }
    }
}
