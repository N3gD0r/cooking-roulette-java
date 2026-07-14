package org.n3gd0r.recipe.usecase;

import org.n3gd0r.infrastructure.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PatchRecipeCommand implements RequestHandler<PatchRecipeParameters, Recipe> {
    private final RecipeRepository repository;

    public PatchRecipeCommand(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Recipe execute(PatchRecipeParameters request) {
        repository.validateExistsById(request.id());
        Recipe recipe = repository.getById(request.id());

        var instructionsOpt = request.instructions();
        var ingredientsOpt = request.ingredients();

        if (instructionsOpt.isPresent()) {
            instructionsOpt.get().stream()
                    .forEach(pi -> {
                        recipe.getInstructions().stream()
                                .filter(i -> i.getId().equals(pi.id()))
                                .forEach(i -> {
                                    i.setStep(pi.stepNumber());
                                    i.setInstruction(pi.stepInstruction());
                                });
                    });
        }

        if (ingredientsOpt.isPresent()) {
            ingredientsOpt.get().stream()
                    .forEach(pi -> {
                        recipe.getIngredients().stream()
                                .filter(i -> i.getId().equals(pi.id()))
                                .forEach(i -> {
                                    i.setIngredientName(pi.ingredientName());
                                    i.setIngredientType(pi.ingredientType());
                                });
                    });
        }

        if (request.name().isPresent()) {
            recipe.setName(request.name().get());
        }
        if (request.cookTime().isPresent()) {
            recipe.setCookTime(request.cookTime().get());
        }
        repository.save(recipe);
        return recipe;
    }
}
