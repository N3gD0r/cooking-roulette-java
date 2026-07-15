package org.n3gd0r.recipe.web.dtos.requests;

import java.util.List;

import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Mass;
import org.n3gd0r.recipe.usecase.RegisterRecipeParameters;
import org.n3gd0r.recipe.usecase.records.RegisterIngredientParameters;
import org.n3gd0r.recipe.usecase.records.RegisterInstructionParameters;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * RegisterRecipeRequest
 */
public record RegisterRecipeWithAllRequest(@Valid @NotNull List<RegisterInstructionRequest> instructions,
        @Valid @NotNull List<RegisterIngredientRequest> ingredients,
        @Valid @NotNull String name,
        @Valid @NotNull @Positive Integer cookTime) {
    public record RegisterInstructionRequest(
            @Positive Integer instructionNumber,
            @NotBlank String instruction) {
        public RegisterInstructionParameters toParameters() {
            return new RegisterInstructionParameters(instructionNumber, instruction);
        }
    }

    public record RegisterIngredientRequest(@NotBlank String ingredientName,
            @NotNull IngredientEnum ingredientType,
            @NotNull @Positive Integer weightInGrams) {
        public RegisterIngredientParameters toParameters() {
            return new RegisterIngredientParameters(ingredientName.toLowerCase(), ingredientType,
                    Mass.ofGrams(weightInGrams));
        }
    }

    public RegisterRecipeParameters toParameters() {
        return new RegisterRecipeParameters(name.toLowerCase(),
                cookTime,
                ingredients.stream().map(RegisterIngredientRequest::toParameters).toList(),
                instructions.stream().map(RegisterInstructionRequest::toParameters).toList());
    }
}
