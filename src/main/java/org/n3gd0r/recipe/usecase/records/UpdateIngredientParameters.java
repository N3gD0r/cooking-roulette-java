package org.n3gd0r.recipe.usecase.records;

import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Mass;
import org.n3gd0r.recipe.domain.RecipeIngredientId;
import org.springframework.util.Assert;

public record UpdateIngredientParameters(RecipeIngredientId id, String ingredientName, IngredientEnum ingredientType,
        Mass weight) {
    public UpdateIngredientParameters {
        Assert.notNull(ingredientName,
                "The RegisterRecipeWithIngredientsParameters ingredientName should not be null");
        Assert.notNull(ingredientType,
                "The RegisterRecipeWithIngredientsParameters ingredientType should not be null");
        Assert.notNull(weight,
                "The RegisterRecipeWithIngredientsParameters weight should not be null");
    }
}
