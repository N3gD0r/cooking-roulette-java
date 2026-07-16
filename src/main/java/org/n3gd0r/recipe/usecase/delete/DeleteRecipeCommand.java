package org.n3gd0r.recipe.usecase.delete;

import org.n3gd0r.infrastructure.mediator.RequestHandler;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * DeleteRecipe
 */
@Component
@Transactional
public class DeleteRecipeCommand implements RequestHandler<DeleteRecipeParameters, Boolean> {
    private final RecipeRepository repository;

    public DeleteRecipeCommand(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Boolean execute(DeleteRecipeParameters request) {
        repository.validateExistsById(request.id());
        repository.deleteById(request.id());
        return true;
    }
}
