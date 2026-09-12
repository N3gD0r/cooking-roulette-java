package org.n3gd0r.recipe.usecase.update;

import java.util.List;

import org.n3gd0r.commons.mediator.HandlerFor;
import org.n3gd0r.commons.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeIngredient;
import org.n3gd0r.recipe.domain.RecipeInstruction;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@HandlerFor(UpdateRecipeParameters.class)
public class UpdateRecipeHandler implements RequestHandler<UpdateRecipeParameters, Recipe> {
    private final RecipeRepository repository;

    public UpdateRecipeHandler(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Recipe execute(UpdateRecipeParameters request) {
        log.info("Updating recipe: {}", request.id());
        repository.validateExistsById(request.recipeUserId(), request.id());
        Recipe recipe = repository.getById(request.recipeUserId(), request.id());
        if (!recipe.getName().equalsIgnoreCase(request.name().trim())) {
            log.debug("Updating recipe name from '{}' to '{}'", recipe.getName(), request.name());
            repository.validateNameUnique(request.recipeUserId(), request.name());
        }
        recipe.setName(request.name());
        recipe.setCookTime(request.cookTime());
        List<RecipeIngredient> ingredients = request.ingredients().stream()
                .map(ingredientParameters -> new RecipeIngredient(
                        repository.nextRecipeIngredientId(),
                        ingredientParameters.ingredientName().trim().toLowerCase(),
                        ingredientParameters.ingredientType(),
                        ingredientParameters.weight()))
                .toList();
        log.debug("Created {} ingredients for update of recipe: {}", ingredients.size(), request.id());
        List<RecipeInstruction> instructions = request.instructions().stream()
                .map(instructionParameters -> new RecipeInstruction(
                        repository.nextRecipeInstructionId(),
                        instructionParameters.instructionNumber(),
                        instructionParameters.instruction()))
                .toList();
        log.debug("Created {} instructions for update of recipe: {}", instructions.size(), request.id());
        recipe.setInstructions(instructions);
        recipe.setIngredients(ingredients);
        repository.save(recipe);
        log.info("Recipe updated successfully: {}", request.id());
        return recipe;
    }
}
