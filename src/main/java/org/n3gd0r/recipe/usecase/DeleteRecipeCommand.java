package org.n3gd0r.recipe.usecase;

import org.n3gd0r.infrastructure.mediator.Command;

/**
 * DeleteRecipeCommand
 */
public class DeleteRecipeCommand extends Command<Boolean> {
    public DeleteRecipeParameters parameters;

    public DeleteRecipeCommand(DeleteRecipeParameters parameters) {
        this.parameters = parameters;
    }
}

