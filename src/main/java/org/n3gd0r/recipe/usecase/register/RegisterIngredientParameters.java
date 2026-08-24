package org.n3gd0r.recipe.usecase.register;

import org.n3gd0r.recipe.domain.IngredientEnum;
import org.springframework.util.Assert;

public record RegisterIngredientParameters(String ingredientName, IngredientEnum ingredientType,
        Integer weight) {
    public RegisterIngredientParameters {
        Assert.hasText(ingredientName,
                "The RegisterIngredientParameters ingredientName should not be blank");
        Assert.notNull(ingredientType,
                "The RegisterIngredientParameters ingredientType should not be null");
        Assert.notNull(weight,
                "The RegisterIngredientParameters weight should not be null");
        Assert.isTrue(weight > 0, "The RegisterIngredientParameters weight should be a positive number");
    }
}
