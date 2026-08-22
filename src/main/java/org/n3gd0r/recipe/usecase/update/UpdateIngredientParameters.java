package org.n3gd0r.recipe.usecase.update;

import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Mass;
import org.springframework.util.Assert;

public record UpdateIngredientParameters(String ingredientName, IngredientEnum ingredientType,
        Mass weight) {
    public UpdateIngredientParameters {
        Assert.hasText(ingredientName,
                "The UpdateIngredientParameters ingredientName should not be blank");
        Assert.notNull(ingredientType,
                "The UpdateIngredientParameters ingredientType should not be null");
        Assert.notNull(weight,
                "The UpdateIngredientParameters weight should not be null");
    }
}
