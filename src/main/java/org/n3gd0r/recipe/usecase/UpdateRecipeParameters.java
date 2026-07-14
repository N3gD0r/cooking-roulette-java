package org.n3gd0r.recipe.usecase;

import java.util.List;

import org.n3gd0r.infrastructure.mediator.Query;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeId;
import org.n3gd0r.recipe.usecase.records.UpdateIngredientParameters;
import org.n3gd0r.recipe.usecase.records.UpdateInstructionParameters;

public class UpdateRecipeParameters extends Query<Recipe> {
    private final RecipeId recipeId;
    private final String name;
    private final Integer cookTime;
    private final List<UpdateIngredientParameters> ingredients;
    private final List<UpdateInstructionParameters> instructions;

    public UpdateRecipeParameters(RecipeId recipeId, String name, Integer cookTime,
            List<UpdateIngredientParameters> ingredients,
            List<UpdateInstructionParameters> instructions) {
        this.recipeId = recipeId;
        this.name = name;
        this.cookTime = cookTime;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public RecipeId recipeId() {
        return recipeId;
    }

    public String name() {
        return name;
    }

    public Integer cookTime() {
        return cookTime;
    }

    public List<UpdateIngredientParameters> ingredients() {
        return ingredients;
    }

    public List<UpdateInstructionParameters> instructions() {
        return instructions;
    }
}
