package org.n3gd0r.recipe.usecase.get;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.n3gd0r.commons.mediator.HandlerFor;
import org.n3gd0r.commons.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * GetAllRecipes
 */
@Slf4j
@HandlerFor(GetAllRecipesParameters.class)
@Service
@Transactional(readOnly = true)
public class GetAllRecipesHandler implements RequestHandler<GetAllRecipesParameters, List<Recipe>> {
    private final RecipeRepository repository;

    public GetAllRecipesHandler(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Recipe> execute(GetAllRecipesParameters request) {
        log.info("Getting all recipes with pageable: {}", request.pageable());
        List<Recipe> recipes = repository.findAll(request.pageable()).toList();
        log.debug("Found {} recipes", recipes.size());
        return recipes;
    }
}
