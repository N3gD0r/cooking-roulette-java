package org.n3gd0r.recipe.web;

import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeIngredient;
import org.n3gd0r.recipe.domain.RecipeStep;

import java.util.List;
import java.util.UUID;

/**
 * RecipeResponse
 */
public record RecipeResponse(UUID id,
                             String name,
                             float cookTime,
                             List<IngredientResponse> ingredients,
                             List<RecipeStepResponse> steps) {

    public static RecipeResponse of(Recipe recipe) {
        return new RecipeResponse(recipe.getId().getId(),
                recipe.getName(),
                recipe.getCookTime(),
                recipe.getIngredients().stream().map(IngredientResponse::of).toList(),
                recipe.getSteps().stream().map(RecipeStepResponse::of).toList());
    }

    public record IngredientResponse(UUID id,
                                     String ingredientName,
                                     IngredientEnum ingredientType,
                                     float weightInGrams) {
        public static IngredientResponse of(RecipeIngredient ingredient) {
            return new IngredientResponse(ingredient.getId().getId(),
                    ingredient.getIngredientName(),
                    ingredient.getIngredientType(),
                    ingredient.getWeight().value());
        }
    }

    public record RecipeStepResponse(UUID id,
                                     int stepNumber,
                                     String stepInstruction) {
        public static RecipeStepResponse of(RecipeStep step) {
            return new RecipeStepResponse(step.getId().getId(),
                    step.getStep(),
                    step.getInstruction());
        }
    }
}
