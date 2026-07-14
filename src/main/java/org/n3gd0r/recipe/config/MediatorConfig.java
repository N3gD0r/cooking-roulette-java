package org.n3gd0r.recipe.config;

import org.n3gd0r.infrastructure.mediator.Mediator;
import org.n3gd0r.recipe.usecase.DeleteRecipeCommand;
import org.n3gd0r.recipe.usecase.DeleteRecipeParameters;
import org.n3gd0r.recipe.usecase.GetAllRecipesParameters;
import org.n3gd0r.recipe.usecase.GetAllRecipesQuery;
import org.n3gd0r.recipe.usecase.GetRecipeParameters;
import org.n3gd0r.recipe.usecase.GetRecipeQuery;
import org.n3gd0r.recipe.usecase.PatchRecipeCommand;
import org.n3gd0r.recipe.usecase.PatchRecipeParameters;
import org.n3gd0r.recipe.usecase.RegisterRecipeCommand;
import org.n3gd0r.recipe.usecase.RegisterRecipeParameters;
import org.n3gd0r.recipe.usecase.UpdateRecipeCommand;
import org.n3gd0r.recipe.usecase.UpdateRecipeParameters;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

/**
 * MediatorConfig
 */
@Configuration
public class MediatorConfig {
    private final Mediator mediator;
    private final RegisterRecipeCommand registerRecipe;
    private final UpdateRecipeCommand updateRecipe;
    private final PatchRecipeCommand patchRecipe;
    private final DeleteRecipeCommand deleteRecipe;
    private final GetRecipeQuery getRecipe;
    private final GetAllRecipesQuery getAllRecipes;

    public MediatorConfig(Mediator mediator, RegisterRecipeCommand registerRecipe, GetRecipeQuery getRecipe,
            DeleteRecipeCommand deleteRecipe, GetAllRecipesQuery getAllRecipes, PatchRecipeCommand patchRecipe,
            UpdateRecipeCommand updateRecipe) {
        this.mediator = mediator;
        this.registerRecipe = registerRecipe;
        this.updateRecipe = updateRecipe;
        this.getRecipe = getRecipe;
        this.deleteRecipe = deleteRecipe;
        this.getAllRecipes = getAllRecipes;
        this.patchRecipe = patchRecipe;
    }

    @PostConstruct
    public void registerHandlers() {
        mediator.registerHandler(RegisterRecipeParameters.class, registerRecipe);
        mediator.registerHandler(GetRecipeParameters.class, getRecipe);
        mediator.registerHandler(DeleteRecipeParameters.class, deleteRecipe);
        mediator.registerHandler(GetAllRecipesParameters.class, getAllRecipes);
        mediator.registerHandler(PatchRecipeParameters.class, patchRecipe);
        mediator.registerHandler(UpdateRecipeParameters.class, updateRecipe);
    }
}
