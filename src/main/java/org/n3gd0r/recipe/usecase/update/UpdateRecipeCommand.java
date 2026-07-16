package org.n3gd0r.recipe.usecase.update;

import java.util.List;
import java.util.stream.Collectors;

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
                .collect(Collectors.toList());
        List<RecipeInstruction> instructions = request.instructions().stream()
                .map(instructionParameters -> new RecipeInstruction(
                        repository.nextRecipeInstructionId(),
                        instructionParameters.instructionNumber(),
                        instructionParameters.instruction()))
                .collect(Collectors.toList());
        recipe.setInstructions(instructions);
        recipe.setIngredients(ingredients);
        repository.save(recipe);
        return recipe;
    }
}
