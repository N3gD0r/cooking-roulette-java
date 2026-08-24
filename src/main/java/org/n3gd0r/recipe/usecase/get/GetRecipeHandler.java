package org.n3gd0r.recipe.usecase.get;

import org.n3gd0r.commons.mediator.HandlerFor;
import org.n3gd0r.commons.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.n3gd0r.recipe.usecase.exception.RequestForHandlerNullPointerException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@HandlerFor(GetRecipeParameters.class)
public class GetRecipeHandler implements RequestHandler<GetRecipeParameters, Recipe> {
    private final RecipeRepository repository;

    public GetRecipeHandler(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Recipe execute(GetRecipeParameters request) {
        if (request == null) {
            log.warn("Request GetRecipeParameters is null");
            throw new RequestForHandlerNullPointerException(GetRecipeParameters.class);
        }
        log.info("Getting recipe by id: {}", request.id());
        return repository.getById(request.id());
    }
}
