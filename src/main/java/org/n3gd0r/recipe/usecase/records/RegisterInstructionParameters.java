package org.n3gd0r.recipe.usecase.records;

import org.springframework.util.Assert;

public record RegisterInstructionParameters(Integer stepNumber, String stepInstruction) {
    public RegisterInstructionParameters {
        Assert.isTrue(stepNumber > 0,
                "The RegisterRecipeWithInstructionsParameters stepNumber should not be less de 1");
        Assert.notNull(stepNumber, "The RegisterRecipeWithInstructionsParameters stepNumber should not be null");
        Assert.hasText(stepInstruction,
                "The RegisterRecipeWithInstructionsParameters stepInstruction should have text");
    }
}
