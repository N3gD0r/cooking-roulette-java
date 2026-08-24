package org.n3gd0r.recipe.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.n3gd0r.recipe.usecase.patch.PatchIngredientParameters;
import org.n3gd0r.recipe.usecase.patch.PatchInstructionParameters;
import org.n3gd0r.recipe.usecase.patch.PatchRecipeParameters;

public final class ParamsMother {
    public static class PatchIngredientsParamsMother {
        public static Builder patchIngredientParamsBuilder() {
            return new Builder();
        }

        public static class Builder {
            private Optional<UUID> id = Optional.empty();
            private Optional<String> ingredientName = Optional.empty();
            private Optional<IngredientEnum> ingredientType = Optional.empty();
            private Optional<Integer> weightInGrams = Optional.empty();

            public Builder id(UUID id) {
                this.id = Optional.ofNullable(id);
                return this;
            }

            public Builder ingredientName(String name) {
                this.ingredientName = Optional.ofNullable(name);
                return this;
            }

            public Builder ingredientType(IngredientEnum ingredientType) {
                this.ingredientType = Optional.ofNullable(ingredientType);
                return this;
            }

            public Builder weight(Integer weight) {
                this.weightInGrams = Optional.ofNullable(weight);
                return this;
            }

            public PatchIngredientParameters build() {
                return new PatchIngredientParameters(id, ingredientName, ingredientType, weightInGrams);
            }
        }
    }

    public static class PatchInstructionParamsMother {
        public static Builder patchInstructionParamsBuilder() {
            return new Builder();
        }

        public static class Builder {
            private Optional<UUID> id = Optional.empty();
            private Optional<String> instruction = Optional.empty();
            private Optional<Integer> instructionNumber = Optional.empty();

            public Builder id(UUID id) {
                this.id = Optional.ofNullable(id);
                return this;
            }

            public Builder instruction(String instruction) {
                this.instruction = Optional.ofNullable(instruction);
                return this;
            }

            public Builder instructionNumber(Integer instructionNumber) {
                this.instructionNumber = Optional.ofNullable(instructionNumber);
                return this;
            }

            public PatchInstructionParameters build() {
                return new PatchInstructionParameters(id, instructionNumber, instruction);
            }
        }
    }

    public static class PatchRecipeParamsMother {
        public static Builder patchRecipeParamsBuilder() {
            return new Builder();
        }

        public static class Builder {
            private UUID id = UUID.randomUUID();
            private Optional<String> name = Optional.empty();
            private Optional<Integer> cookTime = Optional.empty();
            private Optional<List<PatchInstructionParameters>> instructions = Optional.empty();
            private Optional<List<PatchIngredientParameters>> ingredients = Optional.empty();

            public Builder id(UUID id) {
                this.id = id;
                return this;
            }

            public Builder name(String name) {
                this.name = Optional.ofNullable(name);
                return this;
            }

            public Builder cookTime(Integer cookTime) {
                this.cookTime = Optional.ofNullable(cookTime);
                return this;
            }

            public Builder instructions(List<PatchInstructionParameters> instructions) {
                this.instructions = Optional.ofNullable(instructions);
                return this;
            }

            public Builder ingredients(List<PatchIngredientParameters> ingredients) {
                this.ingredients = Optional.ofNullable(ingredients);
                return this;
            }

            public PatchRecipeParameters build() {
                return new PatchRecipeParameters(id, name, cookTime, instructions, ingredients);
            }
        }
    }
}
