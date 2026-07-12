package org.n3gd0r.recipe.usecase;

import java.util.List;

import org.n3gd0r.infrastructure.mediator.Command;
import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Mass;
import org.n3gd0r.recipe.domain.Recipe;
import org.springframework.util.Assert;

/**
 * RegisterRecipeCommand
 */
public class RegisterRecipeCommand extends Command<Recipe> {
    private final String name;
    private final Integer cookTime;
    private final List<RegisterIngredientsParameters> ingredients;
    private final List<RegisterInstructionsParameters> instructions;

    public record RegisterIngredientsParameters(String ingredientName, IngredientEnum ingredientType,
            Mass weight) {
        public RegisterIngredientsParameters {
            Assert.notNull(ingredientName,
                    "The RegisterRecipeWithIngredientsParameters ingredientName should not be null");
            Assert.notNull(ingredientType,
                    "The RegisterRecipeWithIngredientsParameters ingredientType should not be null");
            Assert.notNull(weight,
                    "The RegisterRecipeWithIngredientsParameters weight should not be null");
        }
    }

    public record RegisterInstructionsParameters(Integer stepNumber, String stepInstruction) {
        public RegisterInstructionsParameters {
            Assert.isTrue(stepNumber > 0,
                    "The RegisterRecipeWithInstructionsParameters stepNumber should not be less de 1");
            Assert.notNull(stepNumber, "The RegisterRecipeWithInstructionsParameters stepNumber should not be null");
            Assert.hasText(stepInstruction,
                    "The RegisterRecipeWithInstructionsParameters stepInstruction should have text");
        }
    }

    public RegisterRecipeCommand(String name, Integer cookTime, List<RegisterIngredientsParameters> ingredients,
            List<RegisterInstructionsParameters> instructions) {
        Assert.hasText(name, "The RegisterRecipeCommand name should have text");
        Assert.notNull(cookTime, "The RegisterRecipeCommand cookTime should not be null");
        Assert.isTrue(cookTime > 0, "The RegisterRecipeCommand cookTime should be a positive number");
        Assert.notNull(ingredients, "The RegisterRecipeCommand ingredients should not be null");
        Assert.notEmpty(ingredients, "The RegisterRecipeCommand ingredients should have at least one ingredient");
        Assert.notNull(instructions, "The RegisterRecipeCommand instructions should not be null");
        Assert.notEmpty(instructions,
                "The RegisterRecipeCommand instructions should have at least one recipe instruction");
        this.name = name;
        this.cookTime = cookTime;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public String name() {
        return name;
    }

    public Integer cookTime() {
        return cookTime;
    }

    public List<RegisterIngredientsParameters> ingredients() {
        return ingredients;
    }

    public List<RegisterInstructionsParameters> instructions() {
        return instructions;
    }
}
