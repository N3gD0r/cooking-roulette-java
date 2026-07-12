package org.n3gd0r.recipe.config;

import org.n3gd0r.infrastructure.mediator.Mediator;
import org.n3gd0r.recipe.usecase.DeleteRecipe;
import org.n3gd0r.recipe.usecase.DeleteRecipeCommand;
import org.n3gd0r.recipe.usecase.GetAllRecipes;
import org.n3gd0r.recipe.usecase.GetAllRecipesQuery;
import org.n3gd0r.recipe.usecase.GetRecipe;
import org.n3gd0r.recipe.usecase.GetRecipeQuery;
import org.n3gd0r.recipe.usecase.RegisterRecipe;
import org.n3gd0r.recipe.usecase.RegisterRecipeCommand;
import org.n3gd0r.recipe.usecase.UpdateRecipe;
import org.n3gd0r.recipe.usecase.UpdateRecipeCommand;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

/**
 * MediatorConfig
 */
@Configuration
public class MediatorConfig {
    private final Mediator mediator;
    private final RegisterRecipe registerRecipe;
    private final GetRecipe getRecipe;
    private final DeleteRecipe deleteRecipe;
    private final GetAllRecipes getAllRecipes;
    private final UpdateRecipe updateRecipe;

    public MediatorConfig(Mediator mediator, RegisterRecipe registerRecipe, GetRecipe getRecipe,
            DeleteRecipe deleteRecipe, GetAllRecipes getAllRecipes, UpdateRecipe updateRecipe) {
        this.mediator = mediator;
        this.registerRecipe = registerRecipe;
        this.getRecipe = getRecipe;
        this.deleteRecipe = deleteRecipe;
        this.getAllRecipes = getAllRecipes;
        this.updateRecipe = updateRecipe;
    }

    @PostConstruct
    public void registerHandlers() {
        mediator.registerHandler(RegisterRecipeCommand.class, registerRecipe);
        mediator.registerHandler(GetRecipeQuery.class, getRecipe);
        mediator.registerHandler(DeleteRecipeCommand.class, deleteRecipe);
        mediator.registerHandler(GetAllRecipesQuery.class, getAllRecipes);
        mediator.registerHandler(UpdateRecipeCommand.class, updateRecipe);
    }
}
