package org.n3gd0r.recipe.usecase.get;

import org.n3gd0r.commons.mediator.HandlerFor;
import org.n3gd0r.commons.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@HandlerFor(GetAllRecipesParameters.class)
@Service
public class GetAllRecipesHandler implements RequestHandler<GetAllRecipesParameters, Page<Recipe>> {
    private final RecipeRepository repository;

    public GetAllRecipesHandler(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Recipe> execute(GetAllRecipesParameters request) {
        log.info("Getting all recipes with page: {} and size: {}", request.page(), request.size());
        Page<Recipe> recipes = repository.findAll(PageRequest.of(request.page(), request.size()));
        log.debug("Found recipes {}", recipes);
        return recipes;
    }
}
