package org.n3gd0r.recipe.web;

import java.util.List;

import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Mass;
import org.n3gd0r.recipe.usecase.RegisterRecipeCommand;
import org.n3gd0r.recipe.usecase.RegisterRecipeCommand.RegisterIngredientsParameters;
import org.n3gd0r.recipe.usecase.RegisterRecipeCommand.RegisterInstructionsParameters;

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
            @Positive Integer stepNumber,
            @NotBlank String stepInstruction) {
        public RegisterInstructionsParameters toParameters() {
            return new RegisterInstructionsParameters(stepNumber, stepInstruction);
        }
    }

    public record RegisterIngredientRequest(@NotBlank String ingredientName,
            @NotNull IngredientEnum ingredientType,
            @NotNull @Positive Integer weightInGrams) {
        public RegisterIngredientsParameters toParameters() {
            return new RegisterIngredientsParameters(ingredientName, ingredientType,
                    Mass.ofGrams(weightInGrams));
        }
    }

    public RegisterRecipeCommand toParameters() {
        return new RegisterRecipeCommand(name,
                cookTime,
                ingredients.stream().map(RegisterIngredientRequest::toParameters).toList(),
                instructions.stream().map(RegisterInstructionRequest::toParameters).toList());
    }
}
