package org.n3gd0r.recipe.usecase.register;

import org.springframework.util.Assert;

public record RegisterInstructionParameters(Integer instructionNumber, String instruction) {
    public RegisterInstructionParameters {
        Assert.hasText(instruction,
                "The RegisterInstructionParameters instruction should not be blank");
        Assert.notNull(instructionNumber, "The RegisterInstructionParameters instructionNumber should not be null");
        Assert.isTrue(instructionNumber > 0,
                "The RegisterInstructionParameters instructionNumber should be greater than 0");
    }
}
