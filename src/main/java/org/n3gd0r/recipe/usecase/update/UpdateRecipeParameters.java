package org.n3gd0r.recipe.usecase.update;

import java.util.List;

import org.n3gd0r.commons.mediator.Query;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeId;
import org.springframework.util.Assert;

public record UpdateRecipeParameters(RecipeId recipeId, String name, Integer cookTime,
        List<UpdateIngredientParameters> ingredients, List<UpdateInstructionParameters> instructions)
        implements Query<Recipe> {

    public UpdateRecipeParameters {
        Assert.notNull(recipeId, "The UpdateRecipeParameters recipeId should not be null");
        Assert.notNull(name, "The UpdateRecipeParameters name should not be null");
        Assert.notNull(cookTime, "The UpdateRecipeParameters cookTime should not be null");
        Assert.isTrue(cookTime > 0, "The UpdateRecipeParameters cookTime should be a positive number");
        Assert.notEmpty(ingredients,
                "The UpdateRecipeParameters ingredients should not be null and have at least one element");
        Assert.notEmpty(instructions,
                "The UpdateRecipeParameters instructions should not be null and have at least one element");
    }
}
