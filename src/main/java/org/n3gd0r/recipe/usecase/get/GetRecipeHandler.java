package org.n3gd0r.recipe.usecase.get;

import lombok.extern.slf4j.Slf4j;

import org.n3gd0r.commons.mediator.HandlerFor;
import org.n3gd0r.commons.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.exception.RecipeNotFoundException;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * GetRecipe
 */
@Slf4j
@HandlerFor(GetRecipeParameters.class)
@Service
@Transactional(readOnly = true)
public class GetRecipeHandler implements RequestHandler<GetRecipeParameters, Recipe> {
    private final RecipeRepository repository;

    public GetRecipeHandler(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Recipe execute(GetRecipeParameters request) {
        if (request.id() != null) {
            log.info("Getting recipe by id: {}", request.id());
            return repository.getById(request.id());
        }
        if (request.name() != null) {
            log.info("Getting recipe by name: {}", request.name());
            return repository.findByName(request.name());
        }
        log.error("Recipe query failed: neither id nor name provided");
        throw new RecipeNotFoundException();
    }
}
