package org.n3gd0r.recipe.usecase;

import java.util.List;

import org.n3gd0r.infrastructure.mediator.Command;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.usecase.records.RegisterIngredientParameters;
import org.n3gd0r.recipe.usecase.records.RegisterInstructionParameters;
import org.springframework.util.Assert;

/**
 * RegisterRecipeCommand
 */
public class RegisterRecipeParameters extends Command<Recipe> {
    private final String name;
    private final Integer cookTime;
    private final List<RegisterIngredientParameters> ingredients;
    private final List<RegisterInstructionParameters> instructions;

    public RegisterRecipeParameters(String name, Integer cookTime, List<RegisterIngredientParameters> ingredients,
            List<RegisterInstructionParameters> instructions) {
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

    public List<RegisterIngredientParameters> ingredients() {
        return ingredients;
    }

    public List<RegisterInstructionParameters> instructions() {
        return instructions;
    }
}
