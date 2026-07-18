package org.n3gd0r.recipe.usecase.patch;

import java.util.List;

import org.n3gd0r.infrastructure.mediator.Command;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeId;
import org.springframework.util.Assert;

public class PatchRecipeParameters extends Command<Recipe> {
    private final RecipeId recipeId;
    private final String name;
    private final Integer cookTime;
    private final List<PatchInstructionParameters> instructions;
    private final List<PatchIngredientParameters> ingredients;

    public PatchRecipeParameters(RecipeId recipeId,
            String name,
            Integer cookTime,
            List<PatchIngredientParameters> ingredients,
            List<PatchInstructionParameters> instructions) {
        Assert.notNull(recipeId, "The PatchRecipeParameters recipeId should not be null");
        this.recipeId = recipeId;
        this.name = name;
        this.cookTime = cookTime;
        this.instructions = instructions;
        this.ingredients = ingredients;
    }

    public RecipeId id() {
        return recipeId;
    }

    public List<PatchInstructionParameters> instructions() {
        return instructions;
    }

    public List<PatchIngredientParameters> ingredients() {
        return ingredients;
    }

    public String name() {
        return name;
    }

    public Integer cookTime() {
        return cookTime;
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
