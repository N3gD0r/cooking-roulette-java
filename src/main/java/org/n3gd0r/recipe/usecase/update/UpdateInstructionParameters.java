package org.n3gd0r.recipe.usecase.update;

import org.springframework.util.Assert;

public record UpdateInstructionParameters(Integer instructionNumber, String instruction) {
    public UpdateInstructionParameters {
        Assert.hasText(instruction,
                "The RegisterRecipeWithInstructionsParameters instruction should have text");
        Assert.notNull(instructionNumber,
                "The RegisterRecipeWithInstructionsParameters instructionNumber should not be null");
        Assert.isTrue(instructionNumber > 0,
                "The RegisterRecipeWithInstructionsParameters instructionNumber should not be less de 1");
    }
}
