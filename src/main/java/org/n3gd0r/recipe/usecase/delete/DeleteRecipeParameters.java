package org.n3gd0r.recipe.usecase.delete;

import java.util.UUID;

import org.n3gd0r.commons.mediator.Command;
import org.springframework.util.Assert;

public record DeleteRecipeParameters(UUID recipeUserId, UUID id) implements Command<Void> {
    public DeleteRecipeParameters {
        Assert.notNull(recipeUserId, "The DeleteRecipeCommand recipeUserId should not be null");
        Assert.notNull(id, "The DeleteRecipeCommand id should not be null");
    }
}
