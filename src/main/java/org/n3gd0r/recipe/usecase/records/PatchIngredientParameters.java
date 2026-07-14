package org.n3gd0r.recipe.usecase.records;

import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Mass;
import org.n3gd0r.recipe.domain.RecipeIngredientId;
import org.springframework.util.Assert;

public record PatchIngredientParameters(RecipeIngredientId id, String ingredientName, IngredientEnum ingredientType,
        Mass mass) {
    public PatchIngredientParameters {
        Assert.notNull(id, "The PatchIngredientParameters id should not be null");
    }
}
