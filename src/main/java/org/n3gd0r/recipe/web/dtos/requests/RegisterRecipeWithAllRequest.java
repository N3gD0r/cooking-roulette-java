package org.n3gd0r.recipe.web.dtos.requests;

import java.util.List;
import java.util.UUID;

import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.usecase.register.RegisterIngredientParameters;
import org.n3gd0r.recipe.usecase.register.RegisterInstructionParameters;
import org.n3gd0r.recipe.usecase.register.RegisterRecipeParameters;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Request to register a recipe with all its ingredients and instructions in a
 * single call.
 */
public record RegisterRecipeWithAllRequest(
        @NotNull UUID recipeUserId,
        @NotEmpty List<RegisterInstructionRequest> instructions,
        @NotEmpty List<RegisterIngredientRequest> ingredients,
        @NotBlank String name,
        @Positive Integer cookTime) {

    public RegisterRecipeParameters toParameters() {
        return new RegisterRecipeParameters(
                recipeUserId,
                name,
                cookTime,
                ingredients.stream().map(RegisterIngredientRequest::toParameters).toList(),
                instructions.stream().map(RegisterInstructionRequest::toParameters).toList());
    }

    public record RegisterInstructionRequest(
            @Positive Integer instructionNumber,
            @NotBlank String instruction) {
        public RegisterInstructionParameters toParameters() {
            return new RegisterInstructionParameters(
                    instructionNumber,
                    instruction);
        }
    }

    public record RegisterIngredientRequest(
            @NotBlank String ingredientName,
            @NotNull IngredientEnum ingredientType,
            @NotNull @Positive Integer weightInGrams) {
        public RegisterIngredientParameters toParameters() {
            return new RegisterIngredientParameters(
                    ingredientName,
                    ingredientType,
                    weightInGrams);
        }
    }
}
