package org.n3gd0r.recipe.usecase.register;

import java.util.List;

import org.n3gd0r.commons.mediator.HandlerFor;
import org.n3gd0r.commons.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeId;
import org.n3gd0r.recipe.domain.RecipeIngredient;
import org.n3gd0r.recipe.domain.RecipeInstruction;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

/**
 * RegisterRecipe
 */
@Slf4j
@HandlerFor(RegisterRecipeParameters.class)
@Service
@Transactional
public class RegisterRecipeHandler implements RequestHandler<RegisterRecipeParameters, Recipe> {
    private final RecipeRepository repository;

    public RegisterRecipeHandler(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Recipe execute(RegisterRecipeParameters request) {
        log.info("Registering recipe: {}", request.name());
        repository.validateNameUnique(request.name());
        List<RecipeIngredient> ingredients = request.ingredients().stream()
                .map(ri -> new RecipeIngredient(repository.nextRecipeIngredientId(),
                        ri.ingredientName().trim().toLowerCase(),
                        ri.ingredientType(),
                        ri.weight()))
                .toList();
        log.debug("Created {} ingredients for recipe: {}", ingredients.size(), request.name());

        List<RecipeInstruction> instructions = request.instructions().stream()
                .map(ri -> new RecipeInstruction(repository.nextRecipeInstructionId(),
                        ri.instructionNumber(),
                        ri.instruction()))
                .toList();
        log.debug("Created {} instructions for recipe: {}", instructions.size(), request.name());

        RecipeId id = repository.nextId();
        Recipe recipe = new Recipe(id,
                request.name().trim().toLowerCase(),
                request.cookTime(),
                ingredients,
                instructions);
        repository.save(recipe);
        log.info("Recipe registered successfully: {} ({})", request.name(), id);
        return recipe;
    }
}
