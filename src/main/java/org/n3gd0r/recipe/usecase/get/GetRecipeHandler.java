package org.n3gd0r.recipe.usecase.get;

import org.n3gd0r.commons.mediator.HandlerFor;
import org.n3gd0r.commons.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.exception.RecipeNotFoundException;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@HandlerFor(GetRecipeParameters.class)
@Service
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
            repository.validateEmptyName(request.name().trim());
            log.info("Getting recipe by name: {}", request.name());
            return repository.getByName(request.name().trim().toLowerCase());
        }
        log.error("Recipe query failed: neither id nor name provided");
        throw new RecipeNotFoundException();
    }
}
