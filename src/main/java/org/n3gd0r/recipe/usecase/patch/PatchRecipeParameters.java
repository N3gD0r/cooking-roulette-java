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

    public boolean nothingToPatch() {
        if (recipeId == null) {
            return true;
        }
        if ((name == null || (name != null && name.isBlank())) && cookTime == null && ingredients == null
                && instructions == null) {
            return true;
        }
        if ((name == null || (name != null && name.isBlank())) && cookTime == null && instructions == null
                && ingredients != null
                && ingredients.size() == 0) {
            return true;
        }
        if ((name == null || (name != null && name.isBlank())) && cookTime == null && ingredients == null
                && instructions != null
                && instructions.size() == 0) {
            return true;
        }
        if ((name == null || (name != null && name.isBlank())) && cookTime == null && ingredients != null
                && instructions != null
                && instructions.size() == 0 && ingredients.size() == 0) {
            return true;
        }
        return false;
    }
}
