package org.n3gd0r.recipe.usecase.register;

import java.util.List;

import org.n3gd0r.commons.mediator.Command;
import org.n3gd0r.recipe.domain.Recipe;
import org.springframework.util.Assert;

/**
 * RegisterRecipeCommand
 */
public record RegisterRecipeParameters(String name, Integer cookTime, List<RegisterIngredientParameters> ingredients,
        List<RegisterInstructionParameters> instructions) implements Command<Recipe> {
    public RegisterRecipeParameters {
        Assert.hasText(name, "The RegisterRecipeCommand name should have text");
        Assert.notNull(cookTime, "The RegisterRecipeCommand cookTime should not be null");
        Assert.isTrue(cookTime > 0, "The RegisterRecipeCommand cookTime should be a positive number");
        Assert.notEmpty(ingredients,
                "The RegisterRecipeCommand ingredients should not be null and have at least one ingredient");
        Assert.notEmpty(instructions,
                "The RegisterRecipeCommand instructions should not be null and have at least one recipe instruction");
    }
}
