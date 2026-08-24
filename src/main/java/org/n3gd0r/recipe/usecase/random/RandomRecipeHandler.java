package org.n3gd0r.recipe.usecase.random;

import java.util.concurrent.ThreadLocalRandom;

import org.n3gd0r.commons.mediator.HandlerFor;
import org.n3gd0r.commons.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.exception.NoRecipesFoundException;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.n3gd0r.recipe.usecase.exception.RequestForHandlerNullPointerException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@HandlerFor(RandomRecipeParameters.class)
public class RandomRecipeHandler implements RequestHandler<RandomRecipeParameters, Recipe> {

    private final RecipeRepository repository;

    public RandomRecipeHandler(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Recipe execute(RandomRecipeParameters request) {
        if (request == null) {
            log.warn("Request RandomRecipeParameters is null");
            throw new RequestForHandlerNullPointerException(RandomRecipeParameters.class);
        }
        log.info("Getting random recipe");
        long totalRecipes = repository.count();
        if (totalRecipes == 0) {
            log.warn("No recipes found in database");
            throw new NoRecipesFoundException();
        }
        log.debug("Total recipes available: {}", totalRecipes);
        int randomPage = ThreadLocalRandom.current().nextInt((int) totalRecipes);
        log.debug("Selecting random page: {}", randomPage);
        Recipe foundRecipe = repository.findAll(PageRequest.of(randomPage, 1)).stream()
                .findFirst()
                .orElseThrow(NoRecipesFoundException::new);
        log.info("Random recipe selected: {} ({})", foundRecipe.getName(), foundRecipe.getId());
        return foundRecipe;
    }
}
