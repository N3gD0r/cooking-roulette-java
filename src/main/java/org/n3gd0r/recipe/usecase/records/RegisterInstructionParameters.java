package org.n3gd0r.recipe.usecase.records;

import org.springframework.util.Assert;

public record RegisterInstructionParameters(Integer instructionNumber, String instruction) {
    public RegisterInstructionParameters {
        Assert.isTrue(instructionNumber > 0,
                "The RegisterRecipeWithInstructionsParameters instructionNumber should not be less de 1");
        Assert.notNull(instructionNumber,
                "The RegisterRecipeWithInstructionsParameters instructionNumber should not be null");
        Assert.hasText(instruction,
                "The RegisterRecipeWithInstructionsParameters instruction should have text");
    }
}
