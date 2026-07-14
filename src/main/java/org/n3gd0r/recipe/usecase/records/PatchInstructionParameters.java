package org.n3gd0r.recipe.usecase.records;

import org.n3gd0r.recipe.domain.RecipeStepId;
import org.springframework.util.Assert;

public record PatchInstructionParameters(RecipeStepId id, Integer stepNumber, String stepInstruction) {
    public PatchInstructionParameters {
        Assert.notNull(id, "PatchInstructionParameters id should not be null");
    }
}
