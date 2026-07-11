package org.n3gd0r.recipe.config;

import jakarta.annotation.PostConstruct;
import org.n3gd0r.infrastructure.mediator.Mediator;
import org.n3gd0r.recipe.usecase.*;
import org.springframework.context.annotation.Configuration;

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

    public MediatorConfig(Mediator mediator, RegisterRecipe registerRecipe, GetRecipe getRecipe, DeleteRecipe deleteRecipe, GetAllRecipes getAllRecipes) {
        this.mediator = mediator;
        this.registerRecipe = registerRecipe;
        this.getRecipe = getRecipe;
        this.deleteRecipe = deleteRecipe;
        this.getAllRecipes = getAllRecipes;
    }

    @PostConstruct
    public void registerHandlers() {
        mediator.registerHandler(RegisterRecipeCommand.class, registerRecipe);
        mediator.registerHandler(GetRecipeQuery.class, getRecipe);
        mediator.registerHandler(DeleteRecipeCommand.class, deleteRecipe);
        mediator.registerHandler(GetAllRecipesQuery.class, getAllRecipes);
    }
}
