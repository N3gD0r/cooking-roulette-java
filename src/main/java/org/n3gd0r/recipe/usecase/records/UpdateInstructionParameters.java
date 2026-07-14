package org.n3gd0r.recipe.usecase.records;

import org.n3gd0r.recipe.domain.RecipeStepId;
import org.springframework.util.Assert;

public record UpdateInstructionParameters(RecipeStepId id, Integer stepNumber, String stepInstruction) {
    public UpdateInstructionParameters {
        Assert.isTrue(stepNumber > 0,
                "The RegisterRecipeWithInstructionsParameters stepNumber should not be less de 1");
        Assert.notNull(stepNumber, "The RegisterRecipeWithInstructionsParameters stepNumber should not be null");
        Assert.hasText(stepInstruction,
                "The RegisterRecipeWithInstructionsParameters stepInstruction should have text");
    }
}
