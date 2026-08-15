package org.n3gd0r.recipe.usecase.delete;

import org.n3gd0r.commons.mediator.RequestHandler;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * DeleteRecipe
 */
@Component
@Transactional
public class DeleteRecipeHandler implements RequestHandler<DeleteRecipeParameters, Void> {
    private final RecipeRepository repository;

    public DeleteRecipeHandler(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Void execute(DeleteRecipeParameters request) {
        repository.validateExistsById(request.id());
        repository.deleteById(request.id());
        return null;
    }
}
