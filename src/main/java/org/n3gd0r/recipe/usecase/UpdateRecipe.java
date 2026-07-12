package org.n3gd0r.recipe.usecase;

import org.n3gd0r.infrastructure.mediator.RequestHandler;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UpdateRecipe implements RequestHandler<UpdateRecipeCommand, Boolean> {
    private final RecipeRepository repository;

    public UpdateRecipe(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Boolean execute(UpdateRecipeCommand request) {
        return false;
    }
}
