package org.n3gd0r.recipe.web;

import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Mass;
import org.n3gd0r.recipe.usecase.RegisterRecipeWithAllParameters;
import org.n3gd0r.recipe.usecase.RegisterRecipeWithAllParameters.RegisterRecipeWithIngredientsParameters;
import org.n3gd0r.recipe.usecase.RegisterRecipeWithAllParameters.RegisterRecipeWithInstructionsParameters;
import org.n3gd0r.recipe.usecase.RegisterRecipeWithAllParameters.RegisterRecipeWithParameters;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * RegisterRecipeRequest
 */
public record RegisterRecipeWithAllRequest(@Valid @NotNull RegisterInstructionRequest instruction,
        @Valid @NotNull RegisterIngredientRequest ingredient,
        @Valid @NotNull RegisterRecipeRequest recipe) {
    public record RegisterRecipeRequest(
            @NotBlank String name,
            @NotNull @Positive float cookTime) {
        public RegisterRecipeWithParameters toParameters() {
            return new RegisterRecipeWithParameters(name, cookTime);
        }
    }

    public record RegisterInstructionRequest(
            @NotNull @Positive int stepNumber,
            @NotBlank String stepInstruction) {
        public RegisterRecipeWithInstructionsParameters toParameters() {
            return new RegisterRecipeWithInstructionsParameters(stepNumber, stepInstruction);
        }
    }

    public record RegisterIngredientRequest(@NotBlank String ingredientName,
            @NotNull IngredientEnum ingredientType,
            @NotNull @Positive float weightInGrams) {
        public RegisterRecipeWithIngredientsParameters toParameters() {
            return new RegisterRecipeWithIngredientsParameters(ingredientName, ingredientType,
                    Mass.ofGrams(weightInGrams));
        }
    }

    public RegisterRecipeWithAllParameters toParameters() {
        return new RegisterRecipeWithAllParameters(recipe.toParameters(), ingredient.toParameters(),
                instruction.toParameters());
    }
}
