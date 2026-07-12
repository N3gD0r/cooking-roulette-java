package org.n3gd0r.recipe.usecase;

import java.util.Map;

import org.n3gd0r.infrastructure.mediator.Command;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeId;

public class UpdateRecipeCommand extends Command<Recipe> {
    private final RecipeId recipeId;
    private final Map<String, Object> parameters;

    public UpdateRecipeCommand(RecipeId recipeId, Map<String, Object> parameters) {
        this.recipeId = recipeId;
        this.parameters = parameters;
    }

    public RecipeId id() {
        return recipeId;
    }

    public Map<String, Object> parameters() {
        return parameters;
    }
}
