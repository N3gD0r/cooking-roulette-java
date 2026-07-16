package org.n3gd0r.recipe.usecase.patch;

import org.n3gd0r.recipe.domain.RecipeInstructionId;

public record PatchInstructionParameters(RecipeInstructionId id,
        Integer instructionNumber,
        String instruction) {

    public boolean canAddInstruction() {
        return instructionNumber != null && instruction != null && id == null;
    }
}
