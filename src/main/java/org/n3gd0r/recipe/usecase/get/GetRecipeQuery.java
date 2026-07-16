package org.n3gd0r.recipe.usecase.get;

import org.n3gd0r.infrastructure.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.exception.RecipeNotFoundException;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * GetRecipe
 */
@Component
@Transactional(readOnly = true)
public class GetRecipeQuery implements RequestHandler<GetRecipeParameters, Recipe> {
    private final RecipeRepository repository;

    public GetRecipeQuery(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Recipe execute(GetRecipeParameters request) {
        if (request.id() != null)
            return repository.getById(request.id());
        if (request.name() != null)
            return repository.findByName(request.name());
        throw new RecipeNotFoundException();
    }
}
