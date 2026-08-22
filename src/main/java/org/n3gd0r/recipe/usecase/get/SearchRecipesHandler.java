package org.n3gd0r.recipe.usecase.get;

import java.util.List;

import org.n3gd0r.commons.mediator.HandlerFor;
import org.n3gd0r.commons.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.n3gd0r.recipe.usecase.specifications.FilterRecipeSpecification;
import org.springframework.stereotype.Service;

@HandlerFor(SearchRecipesParameters.class)
@Service
public class SearchRecipesHandler implements RequestHandler<SearchRecipesParameters, List<Recipe>> {
    private final RecipeRepository repository;

    public SearchRecipesHandler(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Recipe> execute(SearchRecipesParameters request) {
        if (request.name().isPresent()) {
            return repository.findAll(FilterRecipeSpecification.hasName(request.name().get()));
        }
        return null;
    }
}
