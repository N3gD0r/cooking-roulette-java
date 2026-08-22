package org.n3gd0r.recipe.usecase.patch;

import java.util.List;
import java.util.UUID;

import org.n3gd0r.commons.mediator.Command;
import org.n3gd0r.recipe.domain.Recipe;
import org.springframework.util.Assert;

public record PatchRecipeParameters(UUID id, String name, Integer cookTime,
        List<PatchInstructionParameters> instructions, List<PatchIngredientParameters> ingredients)
        implements Command<Recipe> {

    public PatchRecipeParameters {
        Assert.notNull(id, "The PatchRecipeParameters id should not be null");
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
