package org.n3gd0r.recipe.usecase;

import org.n3gd0r.infrastructure.mediator.RequestHandler;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * DeleteRecipe
 */
@Component
@Transactional
public class DeleteRecipe implements RequestHandler<DeleteRecipeCommand, Boolean> {
    private final RecipeRepository repository;

    public DeleteRecipe(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Boolean execute(DeleteRecipeCommand request) {
        repository.validateExistsById(request.id());
        repository.deleteById(request.id());
        return true;
    }
}
