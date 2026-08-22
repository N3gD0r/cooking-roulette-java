package org.n3gd0r.recipe.usecase.patch;

import java.util.List;

import org.n3gd0r.commons.mediator.Command;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeId;
import org.springframework.util.Assert;

public record PatchRecipeParameters(RecipeId recipeId, String name, Integer cookTime,
        List<PatchInstructionParameters> instructions, List<PatchIngredientParameters> ingredients)
        implements Command<Recipe> {

    public PatchRecipeParameters {
        Assert.notNull(recipeId, "The PatchRecipeParameters recipeId should not be null");
    }

    /**
     * Returns true if there's nothing to patch: no name, no cookTime, and no
     * meaningful ingredient or instruction changes.
     */
    public boolean nothingToPatch() {
        boolean hasName = name != null && !name.isBlank();
        boolean hasCookTime = cookTime != null;
        boolean hasIngredients = ingredients != null && !ingredients.isEmpty();
        boolean hasInstructions = instructions != null && !instructions.isEmpty();
        return !hasName && !hasCookTime && !hasIngredients && !hasInstructions;
    }
}
