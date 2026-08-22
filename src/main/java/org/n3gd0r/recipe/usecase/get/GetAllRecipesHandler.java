package org.n3gd0r.recipe.usecase.get;

import org.n3gd0r.commons.mediator.HandlerFor;
import org.n3gd0r.commons.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

/**
 * GetAllRecipes
 */
@Slf4j
@HandlerFor(GetAllRecipesParameters.class)
@Service
@Transactional(readOnly = true)
public class GetAllRecipesHandler implements RequestHandler<GetAllRecipesParameters, Page<Recipe>> {
    private final RecipeRepository repository;

    public GetAllRecipesHandler(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Recipe> execute(GetAllRecipesParameters request) {
        log.info("Getting all recipes with pageable: {}", request.pageable());
        Page<Recipe> recipes = repository.findAll(request.pageable());
        log.debug("Found recipes {}", recipes);
        return recipes;
    }
}
