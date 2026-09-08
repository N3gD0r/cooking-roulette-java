package org.n3gd0r.recipe.usecases;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.usecase.patch.PatchIngredientParameters;
import org.n3gd0r.recipe.usecase.patch.PatchInstructionParameters;
import org.n3gd0r.recipe.usecase.patch.PatchRecipeParameters;
import org.n3gd0r.recipe.usecase.register.RegisterIngredientParameters;
import org.n3gd0r.recipe.usecase.register.RegisterInstructionParameters;
import org.n3gd0r.recipe.usecase.register.RegisterRecipeParameters;

public final class ParamsMother {
    public static class PatchIngredientsParamsMother {
        public static Builder builder() {
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
        public static Builder builder() {
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
        public static Builder builder() {
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

    public static class RegisterRecipeParamsMother {
        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private String name = "huevos cocidos";
            private Integer cookTime = 15;
            private List<RegisterIngredientParameters> ingredients = new ArrayList<>();
            private List<RegisterInstructionParameters> instructions = new ArrayList<>();

            public Builder name(String name) {
                this.name = name;
                return this;
            }

            public Builder cookTime(Integer cookTime) {
                this.cookTime = cookTime;
                return this;
            }

            public Builder ingredients(List<RegisterIngredientParameters> ingredients) {
                this.ingredients = ingredients;
                return this;
            }

            public Builder instructions(List<RegisterInstructionParameters> instructions) {
                this.instructions = instructions;
                return this;
            }

            public RegisterRecipeParameters build() {
                return new RegisterRecipeParameters(name, cookTime, ingredients, instructions);
            }
        }
    }

    public static class RegisterIngredientsParamsMother {
        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private String ingredientName = "huevo";
            private IngredientEnum ingredientType = IngredientEnum.CARNES;
            private Integer weight = 1;

            public Builder ingredientName(String ingredientName) {
                this.ingredientName = ingredientName;
                return this;
            }

            public Builder ingredientType(IngredientEnum ingredientType) {
                this.ingredientType = ingredientType;
                return this;
            }

            public Builder weight(Integer weight) {
                this.weight = weight;
                return this;
            }

            public RegisterIngredientParameters build() {
                return new RegisterIngredientParameters(ingredientName, ingredientType, weight);
            }
        }
    }

    public static class RegisterInstructionsParamsMother {
        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private String instruction = "hervir agua";
            private Integer instructionNumber = 1;

            public Builder instruction(String instruction) {
                this.instruction = instruction;
                return this;
            }

            public Builder instructionNumber(Integer instructionNumber) {
                this.instructionNumber = instructionNumber;
                return this;
            }

            public RegisterInstructionParameters build() {
                return new RegisterInstructionParameters(instructionNumber, instruction);
            }
        }
    }
}
