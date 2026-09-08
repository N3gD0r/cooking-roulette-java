package org.n3gd0r.recipe.usecase.register;

import java.util.List;

import org.n3gd0r.commons.mediator.HandlerFor;
import org.n3gd0r.commons.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.Mass;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeIngredient;
import org.n3gd0r.recipe.domain.RecipeInstruction;
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
        repository.validateNameUnique(request.name());
        List<RecipeIngredient> ingredients = request.ingredients().stream()
                .map(ri -> new RecipeIngredient(repository.nextRecipeIngredientId(),
                        ri.ingredientName(),
                        ri.ingredientType(),
                        Mass.ofGrams(ri.weight())))
                .toList();
        log.debug("Created {} ingredients for recipe: {}", ingredients.size(), request.name());

        List<RecipeInstruction> instructions = request.instructions().stream()
                .map(ri -> new RecipeInstruction(repository.nextRecipeInstructionId(),
                        ri.instructionNumber(),
                        ri.instruction()))
                .toList();
        log.debug("Created {} instructions for recipe: {}", instructions.size(), request.name());

        Recipe recipe = new Recipe(repository.nextId(),
                request.name(),
                request.cookTime(),
                ingredients,
                instructions);
        repository.save(recipe);
        log.info("Recipe registered successfully: {} ({})", recipe.getName(), recipe.getId());
        return recipe;
    }
}
