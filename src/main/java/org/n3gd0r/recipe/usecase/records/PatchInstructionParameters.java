package org.n3gd0r.recipe.usecase.records;

import org.n3gd0r.recipe.domain.RecipeInstructionId;
import org.springframework.util.Assert;

public record PatchInstructionParameters(RecipeInstructionId id, Integer instructionNumber, String instruction) {
    public PatchInstructionParameters {
        Assert.notNull(id, "PatchInstructionParameters id should not be null");
    }
}
