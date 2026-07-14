package org.n3gd0r.recipe.usecase;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.n3gd0r.infrastructure.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeIngredient;
import org.n3gd0r.recipe.domain.RecipeStep;
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

        recipe.setName(request.name());
        recipe.setCookTime(request.cookTime());
        recipe.setInstructions(instructions);
        recipe.setIngredients(ingredients);
        repository.save(recipe);
        return recipe;
    }
}
