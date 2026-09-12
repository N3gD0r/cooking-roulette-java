package org.n3gd0r.recipe.usecase.get;

import java.util.List;

import org.n3gd0r.commons.mediator.HandlerFor;
import org.n3gd0r.commons.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@HandlerFor(SearchRecipesParameters.class)
public class SearchRecipesHandler implements RequestHandler<SearchRecipesParameters, List<Recipe>> {
    private final RecipeRepository repository;

    public SearchRecipesHandler(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Recipe> execute(SearchRecipesParameters request) {
        return repository.findAll(request.recipeUserId(), request.toFilterQuery());
    }
}
