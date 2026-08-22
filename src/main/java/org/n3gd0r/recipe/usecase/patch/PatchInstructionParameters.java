package org.n3gd0r.recipe.usecase.patch;

import java.util.UUID;

public record PatchInstructionParameters(UUID id,
        Integer instructionNumber,
        String instruction) {

    public boolean canAddInstruction() {
        return instructionNumber != null && instruction != null && id == null;
    }
}
