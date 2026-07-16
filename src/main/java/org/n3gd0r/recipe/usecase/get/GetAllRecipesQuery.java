package org.n3gd0r.recipe.usecase.get;

import java.util.List;

import org.n3gd0r.infrastructure.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * GetAllRecipes
 */
@Component
@Transactional(readOnly = true)
public class GetAllRecipesQuery implements RequestHandler<GetAllRecipesParameters, List<Recipe>> {
    private final RecipeRepository repository;

    public GetAllRecipesQuery(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Recipe> execute(GetAllRecipesParameters request) {
        return repository.findAll(request.pagination).toList();
    }
}
