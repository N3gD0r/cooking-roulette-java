package org.n3gd0r.recipe.usecase;

import org.n3gd0r.infrastructure.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeId;
import org.n3gd0r.recipe.domain.RecipeIngredient;
import org.n3gd0r.recipe.domain.RecipeStep;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * RegisterRecipe
 */
@Component
@Transactional
public class RegisterRecipe implements RequestHandler<RegisterRecipeCommand, Recipe> {
    private final RecipeRepository repository;

    public RegisterRecipe(RecipeRepository repository) {
        this.repository = repository;
    }

    // TODO Use parallel stream mapping to handle ingredient list and instruction list at the same time
    public Recipe execute(RegisterRecipeCommand request) {
        repository.validateNameUnique(request.parameters.recipeParameters().name());
        var ingredients = request.parameters.ingredientParameters()
                .stream()
                .map(ingredientParameters -> new RecipeIngredient(repository.nextRecipeIngredientId(), ingredientParameters.ingredientName(), ingredientParameters.ingredientType(), ingredientParameters.weight()))
                .toList();

        var instructions = request.parameters.instructionParameters()
                .stream()
                .map(instructionParameters -> new RecipeStep(repository.nextRecipeStepId(), instructionParameters.stepNumber(), instructionParameters.stepInstruction()))
                .toList();

        RecipeId id = repository.nextId();
        Recipe recipe = new Recipe(id,
                request.parameters.recipeParameters().name(),
                request.parameters.recipeParameters().cookTime(),
                ingredients,
                instructions);
        repository.save(recipe);
        return recipe;
    }
}
