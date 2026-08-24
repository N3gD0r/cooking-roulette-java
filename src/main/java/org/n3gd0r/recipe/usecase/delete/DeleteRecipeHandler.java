package org.n3gd0r.recipe.usecase.delete;

import org.n3gd0r.commons.mediator.HandlerFor;
import org.n3gd0r.commons.mediator.RequestHandler;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.n3gd0r.recipe.usecase.exception.RequestForHandlerNullPointerException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * DeleteRecipe
 */
@Slf4j
@Service
@HandlerFor(DeleteRecipeParameters.class)
public class DeleteRecipeHandler implements RequestHandler<DeleteRecipeParameters, Void> {
    private final RecipeRepository repository;

    public DeleteRecipeHandler(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Void execute(DeleteRecipeParameters request) {
        if (request == null) {
            log.warn("Request DeleteRecipeParameters is null");
            throw new RequestForHandlerNullPointerException(DeleteRecipeParameters.class);
        }
        log.info("Deleting recipe: {}", request.id());
        repository.validateExistsById(request.id());
        repository.deleteById(request.id());
        log.info("Recipe deleted successfully: {}", request.id());
        return null;
    }
}
