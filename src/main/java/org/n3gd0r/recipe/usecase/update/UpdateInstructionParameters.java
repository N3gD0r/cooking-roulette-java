package org.n3gd0r.recipe.usecase.update;

import org.springframework.util.Assert;

public record UpdateInstructionParameters(Integer instructionNumber, String instruction) {
    public UpdateInstructionParameters {
        Assert.hasText(instruction,
                "The UpdateInstructionParameters instruction should not be blank");
        Assert.notNull(instructionNumber,
                "The UpdateInstructionParameters instructionNumber should not be null");
        Assert.isTrue(instructionNumber > 0,
                "The UpdateInstructionParameters instructionNumber should be greater than 0");
    }
}
