package org.n3gd0r.recipe.usecase.update;

import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Mass;
import org.springframework.util.Assert;

public record UpdateIngredientParameters(String ingredientName, IngredientEnum ingredientType,
        Mass weight) {
    public UpdateIngredientParameters {
        Assert.hasText(ingredientName,
                "The RegisterRecipeWithIngredientsParameters ingredientName should not be null and must have text");
        Assert.notNull(ingredientType,
                "The RegisterRecipeWithIngredientsParameters ingredientType should not be null");
        Assert.notNull(weight,
                "The RegisterRecipeWithIngredientsParameters weight should not be null");
    }
}
