package org.n3gd0r.recipe.usecase.delete;

import org.n3gd0r.commons.mediator.HandlerFor;
import org.n3gd0r.commons.mediator.RequestHandler;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * DeleteRecipe
 */
@Slf4j
@HandlerFor(DeleteRecipeParameters.class)
@Service
public class DeleteRecipeHandler implements RequestHandler<DeleteRecipeParameters, Void> {
    private final RecipeRepository repository;

    public DeleteRecipeHandler(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Void execute(DeleteRecipeParameters request) {
        log.info("Deleting recipe: {}", request.id());
        repository.validateExistsById(request.id());
        repository.deleteById(request.id());
        log.info("Recipe deleted successfully: {}", request.id());
        return null;
    }
}
