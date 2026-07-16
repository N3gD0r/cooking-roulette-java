package org.n3gd0r.recipe.usecase.update;

import java.util.List;

import org.n3gd0r.infrastructure.mediator.Query;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeId;
import org.springframework.util.Assert;

public class UpdateRecipeParameters extends Query<Recipe> {
    private final RecipeId recipeId;
    private final String name;
    private final Integer cookTime;
    private final List<UpdateIngredientParameters> ingredients;
    private final List<UpdateInstructionParameters> instructions;

    public UpdateRecipeParameters(RecipeId recipeId, String name, Integer cookTime,
            List<UpdateIngredientParameters> ingredients,
            List<UpdateInstructionParameters> instructions) {
        Assert.notNull(recipeId, "The UpdateRecipeParameters recipeId should not be null");
        Assert.notNull(name, "The UpdateRecipeParameters name should not be null");
        Assert.notNull(cookTime, "The UpdateRecipeParameters cookTime should not be null");
        Assert.isTrue(cookTime > 0, "The UpdateRecipeParameters cookTime should be a positive number");
        Assert.notEmpty(ingredients,
                "The UpdateRecipeParameters ingredients should not be null and have at least one element");
        Assert.notEmpty(instructions,
                "The UpdateRecipeParameters instructions should not be null and have at least one element");
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
