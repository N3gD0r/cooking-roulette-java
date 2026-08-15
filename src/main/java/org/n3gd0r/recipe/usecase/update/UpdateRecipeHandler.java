package org.n3gd0r.recipe.usecase.update;

import java.util.List;

import org.n3gd0r.commons.mediator.HandlerFor;
import org.n3gd0r.commons.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeIngredient;
import org.n3gd0r.recipe.domain.RecipeInstruction;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@HandlerFor(UpdateRecipeParameters.class)
@Service
@Transactional
public class UpdateRecipeHandler implements RequestHandler<UpdateRecipeParameters, Recipe> {
    private final RecipeRepository repository;

    public UpdateRecipeHandler(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Recipe execute(UpdateRecipeParameters request) {
        repository.validateExistsById(request.recipeId());
        Recipe recipe = repository.getById(request.recipeId());
        if (!recipe.getName().equalsIgnoreCase(request.name().trim())) {
            repository.validateNameUnique(request.name().trim());
        }
        recipe.setName(request.name().trim().toLowerCase());
        recipe.setCookTime(request.cookTime());
        List<RecipeIngredient> ingredients = request.ingredients().stream()
                .map(ingredientParameters -> new RecipeIngredient(
                        repository.nextRecipeIngredientId(),
                        ingredientParameters.ingredientName().trim().toLowerCase(),
                        ingredientParameters.ingredientType(),
                        ingredientParameters.weight()))
                .toList();
        List<RecipeInstruction> instructions = request.instructions().stream()
                .map(instructionParameters -> new RecipeInstruction(
                        repository.nextRecipeInstructionId(),
                        instructionParameters.instructionNumber(),
                        instructionParameters.instruction()))
                .toList();
        recipe.setInstructions(instructions);
        recipe.setIngredients(ingredients);
        repository.save(recipe);
        return recipe;
    }
}
