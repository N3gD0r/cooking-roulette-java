package org.n3gd0r.recipe.usecase.patch;

import java.util.Optional;
import java.util.UUID;

public record PatchInstructionParameters(
        Optional<UUID> id,
        Optional<Integer> instructionNumber,
        Optional<String> instruction) {

    public boolean canAddInstruction() {
        return id.isEmpty() &&
                instructionNumber.isPresent() &&
                instruction.isPresent();
    }
}
