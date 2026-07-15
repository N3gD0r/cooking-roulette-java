package org.n3gd0r.recipe.usecase;

import java.util.List;

import org.n3gd0r.infrastructure.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeIngredient;
import org.n3gd0r.recipe.domain.RecipeInstruction;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UpdateRecipeCommand implements RequestHandler<UpdateRecipeParameters, Recipe> {
    private final RecipeRepository repository;

    public UpdateRecipeCommand(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Recipe execute(UpdateRecipeParameters request) {
        repository.validateExistsById(request.recipeId());
        repository.validateNameUnique(request.name());
        Recipe recipe = repository.getById(request.recipeId());

        List<RecipeIngredient> ingredients = request.ingredients().stream()
                .map(ingredientParameters -> new RecipeIngredient(ingredientParameters.id(),
                        ingredientParameters.ingredientName(),
                        ingredientParameters.ingredientType(),
                        ingredientParameters.weight()))
                .toList();

        List<RecipeInstruction> instructions = request.instructions().stream()
                .map(instructionParameters -> new RecipeInstruction(instructionParameters.id(),
                        instructionParameters.instructionNumber(),
                        instructionParameters.instruction()))
                .toList();

        recipe.setName(request.name());
        recipe.setCookTime(request.cookTime());
        recipe.setInstructions(instructions);
        recipe.setIngredients(ingredients);
        repository.save(recipe);
        return recipe;
    }
}
