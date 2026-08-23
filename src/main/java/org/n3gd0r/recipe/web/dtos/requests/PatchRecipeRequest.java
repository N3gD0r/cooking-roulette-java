package org.n3gd0r.recipe.web.dtos.requests;

import java.util.List;
import java.util.UUID;

import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Mass;
import org.n3gd0r.recipe.usecase.patch.PatchIngredientParameters;
import org.n3gd0r.recipe.usecase.patch.PatchInstructionParameters;
import org.n3gd0r.recipe.usecase.patch.PatchRecipeParameters;

import jakarta.validation.constraints.NotNull;

/**
 * Request to partially update a recipe.
 * Only provided fields will be patched; null fields are ignored.
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
            return new PatchInstructionParameters(id,
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
            return new PatchIngredientParameters(id,
                    ingredientName,
                    ingredientType,
                    Mass.ofGrams(weightInGrams));
        }
    }

    public PatchRecipeParameters toParameters(@NotNull UUID id) {
        return new PatchRecipeParameters(id,
                name,
                cookTime,
                instructions == null ? null : instructions.stream().map(PatchInstructionRequest::toParameters).toList(),
                ingredients == null ? null : ingredients.stream().map(PatchIngredientRequest::toParameters).toList());
    }
}
