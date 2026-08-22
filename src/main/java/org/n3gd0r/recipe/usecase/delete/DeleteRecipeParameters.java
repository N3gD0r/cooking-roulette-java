package org.n3gd0r.recipe.usecase.delete;

import java.util.UUID;

import org.n3gd0r.commons.mediator.Command;
import org.n3gd0r.recipe.domain.RecipeId;
import org.springframework.util.Assert;

/**
 * DeleteRecipeCommand
 */
public record DeleteRecipeParameters(RecipeId id) implements Command<Void> {
    public DeleteRecipeParameters {
        Assert.notNull(id, "The DeleteRecipeCommand id should not be null");
    }

    public static DeleteRecipeParameters recipeId(UUID id) {
        return new DeleteRecipeParameters(new RecipeId(id));
    }
}
