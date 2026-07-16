package org.n3gd0r.recipe.usecase.delete;

import org.n3gd0r.infrastructure.mediator.Command;
import org.n3gd0r.recipe.domain.RecipeId;
import org.springframework.util.Assert;

/**
 * DeleteRecipeCommand
 */
public class DeleteRecipeParameters extends Command<Boolean> {
    private final RecipeId id;

    public DeleteRecipeParameters(RecipeId id) {
        Assert.notNull(id, "The DeleteRecipeCommand id should not be null");
        this.id = id;
    }

    public RecipeId id() {
        return id;
    }
}
