package org.n3gd0r.recipe.web.dtos.requests;

import java.util.List;
import java.util.UUID;

import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Mass;
import org.n3gd0r.recipe.domain.RecipeId;
import org.n3gd0r.recipe.domain.RecipeIngredientId;
import org.n3gd0r.recipe.domain.RecipeInstructionId;
import org.n3gd0r.recipe.usecase.UpdateRecipeParameters;
import org.n3gd0r.recipe.usecase.records.UpdateIngredientParameters;
import org.n3gd0r.recipe.usecase.records.UpdateInstructionParameters;

/**
 * RegisterRecipeRequest
 */
public record UpdateRecipeRequest(
        String name,
        Integer cookTime,
        List<UpdateIngredientRequest> ingredients,
        List<UpdateInstructionRequest> instructions) {
    public record UpdateInstructionRequest(
            UUID id,
            Integer instructionNumber,
            String instruction) {
        public UpdateInstructionParameters toParameters() {
            return new UpdateInstructionParameters(new RecipeInstructionId(id), instructionNumber, instruction);
        }
    }

    public record UpdateIngredientRequest(
            UUID id,
            String ingredientName,
            IngredientEnum ingredientType,
            Integer weightInGrams) {
        public UpdateIngredientParameters toParameters() {
            return new UpdateIngredientParameters(new RecipeIngredientId(id), ingredientName.toLowerCase(),
                    ingredientType,
                    Mass.ofGrams(weightInGrams));
        }
    }

    public UpdateRecipeParameters toParameters(UUID id) {
        return new UpdateRecipeParameters(new RecipeId(id),
                name.toLowerCase(),
                cookTime,
                ingredients.stream()
                        .map(UpdateIngredientRequest::toParameters)
                        .toList(),
                instructions.stream()
                        .map(UpdateInstructionRequest::toParameters)
                        .toList());
    }
}
