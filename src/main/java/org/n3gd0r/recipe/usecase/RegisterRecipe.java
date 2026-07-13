package org.n3gd0r.recipe.usecase;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

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

    public Recipe execute(RegisterRecipeCommand request) {
        repository.validateNameUnique(request.name());
        CompletableFuture<List<RecipeIngredient>> cfIngredients = CompletableFuture
                .supplyAsync(() -> request.ingredients().stream()
                        .map(ingredientParameters -> new RecipeIngredient(repository.nextRecipeIngredientId(),
                                ingredientParameters.ingredientName(), ingredientParameters.ingredientType(),
                                ingredientParameters.weight()))
                        .collect(Collectors.toList()));

        CompletableFuture<List<RecipeStep>> cfInstructions = CompletableFuture
                .supplyAsync(() -> request.instructions().stream()
                        .map(instructionParameters -> new RecipeStep(repository.nextRecipeStepId(),
                                instructionParameters.stepNumber(), instructionParameters.stepInstruction()))
                        .collect(Collectors.toList()));

        CompletableFuture.allOf(cfIngredients, cfInstructions).join();

        List<RecipeIngredient> ingredients = cfIngredients.join();

        List<RecipeStep> instructions = cfInstructions.join();

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
