package org.n3gd0r.recipe.usecase;

import org.n3gd0r.infrastructure.mediator.Command;
import org.n3gd0r.recipe.domain.Recipe;

/**
 * RegisterRecipeCommand
 */
public class RegisterRecipeCommand extends Command<Recipe> {
    public RegisterRecipeWithAllParameters parameters;

    public RegisterRecipeCommand(RegisterRecipeWithAllParameters parameters) {
        this.parameters = parameters;
    }
}
