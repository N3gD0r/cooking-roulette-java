package org.n3gd0r.recipe.usecase.random;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.n3gd0r.commons.mediator.HandlerFor;
import org.n3gd0r.commons.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.exception.NoRecipesForFiltersException;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@HandlerFor(RandomRecipeFiltersParameters.class)
public class RandomRecipeWithFiltersHandler implements RequestHandler<RandomRecipeFiltersParameters, Recipe> {
    private final RecipeRepository repository;

    public RandomRecipeWithFiltersHandler(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Recipe execute(RandomRecipeFiltersParameters request) {
        log.info("RandomRecipeWithFiltersHandler - Getting random recipe with filters");
        List<Recipe> recipes = repository.findAll(request.recipeUserId(), request.toFilterQuery());
        long foundRecipes = recipes.size();
        if (foundRecipes < 1) {
            log.warn("No recipes matched the given filters");
            throw new NoRecipesForFiltersException();
        }
        if (foundRecipes == 1) {
            log.debug("Only one recipe matched filters, returning it");
            return recipes.getFirst();
        }

        int randomIndex = ThreadLocalRandom.current().nextInt((int) foundRecipes);
        Recipe randomRecipe = recipes.get(randomIndex);

        log.info("Random recipe selected: {} ({})", randomRecipe.getName(), randomRecipe.getId());
        return randomRecipe;
    }
}
