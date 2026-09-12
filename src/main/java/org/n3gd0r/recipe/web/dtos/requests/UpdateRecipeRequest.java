package org.n3gd0r.recipe.web.dtos.requests;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Mass;
import org.n3gd0r.recipe.usecase.update.UpdateIngredientParameters;
import org.n3gd0r.recipe.usecase.update.UpdateInstructionParameters;
import org.n3gd0r.recipe.usecase.update.UpdateRecipeParameters;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateRecipeRequest(
        @NotBlank String name,
        @Positive Integer cookTime,
        @NotEmpty List<UpdateIngredientRequest> ingredients,
        @NotEmpty List<UpdateInstructionRequest> instructions) {
    public record UpdateInstructionRequest(
            @Positive Integer instructionNumber,
            @NotBlank String instruction) {
        public UpdateInstructionParameters toParameters() {
            return new UpdateInstructionParameters(instructionNumber, instruction);
        }
    }

    public record UpdateIngredientRequest(
            @NotBlank String ingredientName,
            @NotNull IngredientEnum ingredientType,
            @Positive Integer weightInGrams) {
        public UpdateIngredientParameters toParameters() {
            return new UpdateIngredientParameters(ingredientName,
                    ingredientType,
                    Mass.ofGrams(weightInGrams));
        }
    }

    public UpdateRecipeParameters toParameters(UUID id, UUID recipeUserId) {
        return new UpdateRecipeParameters(id,
                recipeUserId,
                name.toLowerCase(),
                cookTime,
                ingredients.stream()
                        .map(UpdateIngredientRequest::toParameters)
                        .collect(Collectors.toList()),
                instructions.stream()
                        .map(UpdateInstructionRequest::toParameters)
                        .collect(Collectors.toList()));
    }
}
