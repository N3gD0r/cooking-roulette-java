package org.n3gd0r.recipe.usecase;

import org.n3gd0r.infrastructure.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.RecipeId;
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
        RecipeId recipeId = new RecipeId(request.parameters.id());
        repository.validateExistsById(recipeId);
        repository.deleteById(recipeId);
        return true;
    }
}
