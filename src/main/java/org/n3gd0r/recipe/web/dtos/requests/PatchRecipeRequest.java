package org.n3gd0r.recipe.web.dtos.requests;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
        Optional<String> name,
        Optional<Integer> cookTime,
        Optional<List<PatchIngredientRequest>> ingredients,
        Optional<List<PatchInstructionRequest>> instructions) {
    public record PatchInstructionRequest(
            UUID id,
            Integer instructionNumber,
            String instruction) {
        public PatchInstructionParameters toParameters() {
            return new PatchInstructionParameters(new RecipeInstructionId(id), instructionNumber, instruction);
        }
    }

    public record PatchIngredientRequest(
            UUID id,
            String ingredientName,
            IngredientEnum ingredientType,
            Integer weightInGrams) {
        public PatchIngredientParameters toParameters() {
            return new PatchIngredientParameters(new RecipeIngredientId(id), ingredientName.toLowerCase(),
                    ingredientType,
                    Mass.ofGrams(weightInGrams));
        }
    }

    public PatchRecipeParameters toParameters(UUID id) {
        return new PatchRecipeParameters(new RecipeId(id),
                name,
                cookTime,
                ingredients.map(liopt -> liopt.stream()
                        .map(PatchIngredientRequest::toParameters)
                        .collect(Collectors.toList())),
                instructions.map(liopt -> liopt.stream()
                        .map(PatchInstructionRequest::toParameters)
                        .collect(Collectors.toList())));
    }
}
