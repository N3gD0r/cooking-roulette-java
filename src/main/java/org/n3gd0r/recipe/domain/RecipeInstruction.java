package org.n3gd0r.recipe.domain;

import org.n3gd0r.commons.AbstractEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class RecipeInstruction extends AbstractEntity<RecipeInstructionId> {
    private int instructionNumber;
    private String instruction;
    @ManyToOne(fetch = FetchType.LAZY)
    private Recipe recipe;

    protected RecipeInstruction() {
    }

    public RecipeInstruction(RecipeInstructionId id, int instructionNumber, String instruction) {
        super(id);
        this.instructionNumber = instructionNumber;
        this.instruction = instruction;
    }
}
