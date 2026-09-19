package org.n3gd0r.recipe.usecase.register;

import org.n3gd0r.commons.mediator.HandlerFor;
import org.n3gd0r.commons.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@HandlerFor(RegisterRecipeParameters.class)
public class RegisterRecipeHandler implements RequestHandler<RegisterRecipeParameters, Recipe> {
    private final RecipeRepository repository;

    public RegisterRecipeHandler(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Recipe execute(RegisterRecipeParameters request) {
        log.info("Registering recipe: {}", request.name());
        repository.validateNameUnique(request.recipeUserId(), request.name());
        Recipe recipe = request.toRecipe(repository::nextId,
                repository::nextRecipeIngredientId,
                repository::nextRecipeInstructionId);
        repository.save(recipe);
        log.info("Recipe registered successfully: {} ({})", recipe.getName(), recipe.getId());
        return recipe;
    }
}
