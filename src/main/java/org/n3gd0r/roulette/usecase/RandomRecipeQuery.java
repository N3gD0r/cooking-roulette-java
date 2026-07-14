package org.n3gd0r.roulette.usecase;

import java.util.Random;

import org.n3gd0r.infrastructure.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.n3gd0r.roulette.domain.exception.NoRecipesFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class RandomRecipeQuery implements RequestHandler<RandomRecipeParameters, Recipe> {

    private final RecipeRepository repository;

    public RandomRecipeQuery(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Recipe execute(RandomRecipeParameters request) {
        long totalRecipes = repository.count();
        int randomPage = new Random().nextInt((int) totalRecipes);
        Recipe foundRecipe = repository.findAll(PageRequest.of(randomPage, 1)).stream()
                .findFirst()
                .orElseThrow(NoRecipesFoundException::new);
        return foundRecipe;
    }
}
