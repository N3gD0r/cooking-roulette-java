package org.n3gd0r.infrastructure;

import org.n3gd0r.commons.mediator.Mediator;
import org.n3gd0r.recipe.usecase.delete.DeleteRecipeHandler;
import org.n3gd0r.recipe.usecase.delete.DeleteRecipeParameters;
import org.n3gd0r.recipe.usecase.get.GetAllRecipesHandler;
import org.n3gd0r.recipe.usecase.get.GetAllRecipesParameters;
import org.n3gd0r.recipe.usecase.get.GetRecipeHandler;
import org.n3gd0r.recipe.usecase.get.GetRecipeParameters;
import org.n3gd0r.recipe.usecase.patch.PatchRecipeHandler;
import org.n3gd0r.recipe.usecase.patch.PatchRecipeParameters;
import org.n3gd0r.recipe.usecase.register.RegisterRecipeHandler;
import org.n3gd0r.recipe.usecase.register.RegisterRecipeParameters;
import org.n3gd0r.recipe.usecase.update.UpdateRecipeHandler;
import org.n3gd0r.recipe.usecase.update.UpdateRecipeParameters;
import org.n3gd0r.roulette.usecase.RandomRecipeFiltersParameters;
import org.n3gd0r.roulette.usecase.RandomRecipeHandler;
import org.n3gd0r.roulette.usecase.RandomRecipeParameters;
import org.n3gd0r.roulette.usecase.RandomRecipeWithFiltersHandler;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

/**
 * MediatorConfig
 */
@Configuration
public class MediatorConfig {
    private final Mediator mediator;
    private final RegisterRecipeHandler registerRecipe;
    private final UpdateRecipeHandler updateRecipe;
    private final PatchRecipeHandler patchRecipe;
    private final DeleteRecipeHandler deleteRecipe;
    private final GetRecipeHandler getRecipe;
    private final GetAllRecipesHandler getAllRecipes;
    private final RandomRecipeHandler getRandomRecipe;
    private final RandomRecipeWithFiltersHandler getRandomRecipeWithFilters;

    public MediatorConfig(Mediator mediator, RegisterRecipeHandler registerRecipe, GetRecipeHandler getRecipe,
            DeleteRecipeHandler deleteRecipe, GetAllRecipesHandler getAllRecipes, PatchRecipeHandler patchRecipe,
            UpdateRecipeHandler updateRecipe, RandomRecipeHandler getRandomRecipe,
            RandomRecipeWithFiltersHandler getRandomRecipeWithFilters) {
        this.mediator = mediator;
        this.registerRecipe = registerRecipe;
        this.updateRecipe = updateRecipe;
        this.getRecipe = getRecipe;
        this.deleteRecipe = deleteRecipe;
        this.getAllRecipes = getAllRecipes;
        this.patchRecipe = patchRecipe;
        this.getRandomRecipe = getRandomRecipe;
        this.getRandomRecipeWithFilters = getRandomRecipeWithFilters;
    }

    @PostConstruct
    public void registerHandlers() {
        mediator.registerHandler(RegisterRecipeParameters.class, registerRecipe);
        mediator.registerHandler(GetRecipeParameters.class, getRecipe);
        mediator.registerHandler(DeleteRecipeParameters.class, deleteRecipe);
        mediator.registerHandler(GetAllRecipesParameters.class, getAllRecipes);
        mediator.registerHandler(PatchRecipeParameters.class, patchRecipe);
        mediator.registerHandler(UpdateRecipeParameters.class, updateRecipe);
        mediator.registerHandler(RandomRecipeParameters.class, getRandomRecipe);
        mediator.registerHandler(RandomRecipeFiltersParameters.class, getRandomRecipeWithFilters);
    }
}
