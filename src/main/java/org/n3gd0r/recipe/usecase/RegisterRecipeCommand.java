package org.n3gd0r.recipe.usecase;

import java.util.List;
import java.util.stream.Collectors;

import org.n3gd0r.infrastructure.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeId;
import org.n3gd0r.recipe.domain.RecipeIngredient;
import org.n3gd0r.recipe.domain.RecipeInstruction;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * RegisterRecipe
 */
@Component
@Transactional
public class RegisterRecipeCommand implements RequestHandler<RegisterRecipeParameters, Recipe> {
    private final RecipeRepository repository;

    public RegisterRecipeCommand(RecipeRepository repository) {
        this.repository = repository;
    }

    public Recipe execute(RegisterRecipeParameters request) {
        repository.validateNameUnique(request.name());
        List<RecipeIngredient> ingredients = request.ingredients().stream()
                .map(ip -> new RecipeIngredient(repository.nextRecipeIngredientId(),
                        ip.ingredientName(),
                        ip.ingredientType(),
                        ip.weight()))
                .collect(Collectors.toList());

        List<RecipeInstruction> instructions = request.instructions().stream()
                .map(ip -> new RecipeInstruction(repository.nextRecipeInstructionId(),
                        ip.instructionNumber(),
                        ip.instruction()))
                .collect(Collectors.toList());

        RecipeId id = repository.nextId();
        Recipe recipe = new Recipe(id,
                request.name(),
                request.cookTime(),
                ingredients,
                instructions);
        repository.save(recipe);
        return recipe;
    }
}
