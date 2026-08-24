package org.n3gd0r.recipe.web.dtos.requests;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.usecase.patch.PatchIngredientParameters;
import org.n3gd0r.recipe.usecase.patch.PatchInstructionParameters;
import org.n3gd0r.recipe.usecase.patch.PatchRecipeParameters;

import jakarta.validation.constraints.NotNull;

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
            return new PatchInstructionParameters(
                    Optional.ofNullable(id),
                    Optional.ofNullable(instructionNumber),
                    Optional.ofNullable(instruction));
        }
    }

    public record PatchIngredientRequest(
            UUID id,
            String ingredientName,
            IngredientEnum ingredientType,
            Integer weightInGrams) {
        public PatchIngredientParameters toParameters() {
            return new PatchIngredientParameters(
                    Optional.ofNullable(id),
                    Optional.ofNullable(ingredientName),
                    Optional.ofNullable(ingredientType),
                    Optional.ofNullable(weightInGrams));
        }
    }

    public PatchRecipeParameters toParameters(@NotNull UUID id) {
        return new PatchRecipeParameters(
                id,
                Optional.ofNullable(name),
                Optional.ofNullable(cookTime),
                instructions == null ? Optional.empty()
                        : Optional.ofNullable(instructions.stream()
                                .map(PatchInstructionRequest::toParameters)
                                .toList()),
                ingredients == null ? Optional.empty()
                        : Optional.ofNullable(ingredients.stream()
                                .map(PatchIngredientRequest::toParameters)
                                .toList()));
    }
}
