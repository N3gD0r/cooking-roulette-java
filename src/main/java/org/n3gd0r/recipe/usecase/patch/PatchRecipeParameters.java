package org.n3gd0r.recipe.usecase.patch;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.n3gd0r.commons.mediator.Command;
import org.n3gd0r.recipe.domain.Recipe;
import org.springframework.util.Assert;

public record PatchRecipeParameters(
        UUID id,
        UUID recipeUserId,
        Optional<String> name,
        Optional<Integer> cookTime,
        Optional<List<PatchInstructionParameters>> instructions,
        Optional<List<PatchIngredientParameters>> ingredients) implements Command<Recipe> {

    public PatchRecipeParameters {
        Assert.notNull(id, "The PatchRecipeParameters id should not be null");
        Assert.notNull(recipeUserId, "The PatchRecipeParameters recipeUserId should not be null");
    }

    /**
     * Returns true if there's nothing to patch: no name, no cookTime, and no
     * meaningful ingredient or instruction changes.
     */
    public boolean nothingToPatch() {
        return (name.isEmpty() ? true : name.get().isBlank()) &&
                (cookTime.isEmpty() ? true : cookTime.get() < 1) &&
                (ingredients.isEmpty() ? true : ingredients.get().size() == 0) &&
                (instructions.isEmpty() ? true : instructions.get().size() == 0);
    }
}
