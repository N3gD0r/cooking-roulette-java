package org.n3gd0r.recipe.web.dtos.requests;

import java.util.List;
import java.util.UUID;

import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Mass;
import org.n3gd0r.recipe.domain.RecipeId;
import org.n3gd0r.recipe.domain.RecipeIngredientId;
import org.n3gd0r.recipe.domain.RecipeInstructionId;
import org.n3gd0r.recipe.usecase.PatchRecipeParameters;
import org.n3gd0r.recipe.usecase.records.PatchIngredientParameters;
import org.n3gd0r.recipe.usecase.records.PatchInstructionParameters;

/**
 * RegisterRecipeRequest
 */
public record PatchRecipeRequest(
        String name,
        Integer cookTime,
        List<PatchIngredientRequest> ingredients,
        List<PatchInstructionRequest> instructions) {
    public record PatchInstructionRequest(
            UUID id,
            Integer instructionNumber,
            String instruction) {
        public PatchInstructionParameters toParameters() {
            return new PatchInstructionParameters(id != null ? new RecipeInstructionId(id) : null,
                    instructionNumber,
                    instruction);
        }
    }

    public record PatchIngredientRequest(
            UUID id,
            String ingredientName,
            IngredientEnum ingredientType,
            Integer weightInGrams) {
        public PatchIngredientParameters toParameters() {
            return new PatchIngredientParameters(id != null ? new RecipeIngredientId(id) : null,
                    ingredientName.toLowerCase(),
                    ingredientType,
                    Mass.ofGrams(weightInGrams));
        }
    }

    public PatchRecipeParameters toParameters(UUID id) {
        return new PatchRecipeParameters(id != null ? new RecipeId(id) : null,
                name,
                cookTime,
                ingredients == null ? null
                        : ingredients.stream()
                                .map(PatchIngredientRequest::toParameters)
                                .toList(),
                instructions == null ? null
                        : instructions.stream()
                                .map(PatchInstructionRequest::toParameters)
                                .toList());
    }
}
