package org.n3gd0r.roulette.usecase;

import java.util.Random;

import org.n3gd0r.commons.mediator.HandlerFor;
import org.n3gd0r.commons.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.n3gd0r.roulette.domain.exception.NoRecipesFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@HandlerFor(RandomRecipeParameters.class)
@Service
@Transactional(readOnly = true)
public class RandomRecipeHandler implements RequestHandler<RandomRecipeParameters, Recipe> {

    private final RecipeRepository repository;

    public RandomRecipeHandler(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Recipe execute(RandomRecipeParameters request) {
        long totalRecipes = repository.count();
        if (totalRecipes == 0) {
            throw new NoRecipesFoundException();
        }
        int randomPage = new Random().nextInt((int) totalRecipes);
        Recipe foundRecipe = repository.findAll(PageRequest.of(randomPage, 1)).stream()
                .findFirst()
                .orElseThrow(NoRecipesFoundException::new);
        return foundRecipe;
    }
}
