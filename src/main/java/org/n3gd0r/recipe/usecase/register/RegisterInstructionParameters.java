package org.n3gd0r.recipe.usecase.register;

import org.springframework.util.Assert;

public record RegisterInstructionParameters(Integer instructionNumber, String instruction) {
    public RegisterInstructionParameters {
        Assert.hasText(instruction,
                "The RegisterRecipeWithInstructionsParameters instruction should have text");
        Assert.notNull(instructionNumber, "The RegisterInstructionParameters instructionNumber should not be null");
        Assert.isTrue(instructionNumber > 0,
                "The RegisterRecipeWithInstructionsParameters instructionNumber should not be less de 1");
    }
}
