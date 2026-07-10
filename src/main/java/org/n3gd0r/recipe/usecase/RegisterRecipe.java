package org.n3gd0r.recipe.usecase;

import java.util.Collections;

import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeId;
import org.n3gd0r.recipe.domain.RecipeIngredient;
import org.n3gd0r.recipe.domain.RecipeIngredientId;
import org.n3gd0r.recipe.domain.RecipeStep;
import org.n3gd0r.recipe.domain.RecipeStepId;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * RegisterRecipe
 */
@Component
@Transactional
public class RegisterRecipe {
    private final RecipeRepository repository;

    public RegisterRecipe(RecipeRepository repository) {
        this.repository = repository;
    }

    public Recipe execute(RegisterRecipeWithAllParameters parameters) {
        RecipeStepId recipeStepId = repository.nextRecipeStepId();
        RecipeStep recipeStep = new RecipeStep(recipeStepId,
                parameters.instructionParameters().stepNumber(),
                parameters.instructionParameters().stepInstruction());

        RecipeIngredientId recipeIngredientId = repository.nextRecipeIngredientId();
        RecipeIngredient recipeIngredient = new RecipeIngredient(recipeIngredientId,
                parameters.ingredientParameters().ingredientName(),
                parameters.ingredientParameters().ingredientType(),
                parameters.ingredientParameters().weight());

        RecipeId id = repository.nextId();
        Recipe recipe = new Recipe(id,
                parameters.recipeParameters().name(),
                parameters.recipeParameters().cookTime(),
                Collections.singletonList(recipeIngredient),
                Collections.singletonList(recipeStep));
        repository.save(recipe);
        return recipe;
    }
}
