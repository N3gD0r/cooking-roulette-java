package org.n3gd0r.recipe.domain;

import java.util.UUID;

public final class RecipeInstructionMother {
    public static Builder recipeInstruction() {
        return new Builder();
    }

    public static final class Builder {
        private RecipeInstructionId id = new RecipeInstructionId(UUID.randomUUID());
        private int instructionNumber = 1;
        private String instruction = "Hervir el agua";

        public Builder id(RecipeInstructionId id) {
            this.id = id;
            return this;
        }

        public Builder instructionNumber(int instructionNumber) {
            this.instructionNumber = instructionNumber;
            return this;
        }

        public Builder instruction(String instruction) {
            this.instruction = instruction;
            return this;
        }

        public RecipeInstruction build() {
            return new RecipeInstruction(id, instructionNumber, instruction);
        }
    }
}
