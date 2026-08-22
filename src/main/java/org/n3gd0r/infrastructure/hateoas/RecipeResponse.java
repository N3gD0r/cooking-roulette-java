package org.n3gd0r.infrastructure.hateoas;

import java.util.List;
import java.util.UUID;

import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeIngredient;
import org.n3gd0r.recipe.domain.RecipeInstruction;

public record RecipeResponse(UUID id, String name, int cookTime, List<IngredientResponse> ingredients,
        List<InstructionResponse> instructions) {
    public static RecipeResponse of(Recipe recipe) {
        var recipeIngredients = recipe.getIngredients().stream()
                .map(IngredientResponse::of)
                .toList();
        var recipeInstructions = recipe.getInstructions().stream()
                .map(InstructionResponse::of)
                .toList();
        return new RecipeResponse(recipe.getId().getId(), recipe.getName(), recipe.getCookTime(), recipeIngredients,
                recipeInstructions);
    }

    public record IngredientResponse(UUID id, String ingredientName, IngredientEnum ingredientType, int weightInGrams) {
        public static IngredientResponse of(RecipeIngredient ingredient) {
            return new IngredientResponse(ingredient.getId().getId(), ingredient.getIngredientName(),
                    ingredient.getIngredientType(), ingredient.getWeight().value());
        }
    }

    public record InstructionResponse(UUID id, String instruction, int instructionNumber) {
        public static InstructionResponse of(RecipeInstruction instruction) {
            return new InstructionResponse(instruction.getId().getId(), instruction.getInstruction(),
                    instruction.getInstructionNumber());
        }
    }
}
