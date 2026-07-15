package org.n3gd0r.recipe.domain;

import org.n3gd0r.commons.AbstractEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

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

    public int getInstructionNumber() {
        return instructionNumber;
    }

    public void setInstructionNumber(int instructionNumber) {
        this.instructionNumber = instructionNumber;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
