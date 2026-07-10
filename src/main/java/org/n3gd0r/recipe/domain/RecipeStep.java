package org.n3gd0r.recipe.domain;

import org.n3gd0r.commons.AbstractEntity;

import jakarta.persistence.Entity;

@Entity
public class RecipeStep extends AbstractEntity<RecipeStepId> {
    private int stepNumber;
    private String stepInstruction;

    protected RecipeStep() {
    }

    public RecipeStep(RecipeStepId id, int stepNumber, String stepInstruction) {
        super(id);
        this.stepNumber = stepNumber;
        this.stepInstruction = stepInstruction;
    }

    public int getStep() {
        return stepNumber;
    }

    public void setStep(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public String getInstruction() {
        return stepInstruction;
    }

    public void setInstruction(String stepInstruction) {
        this.stepInstruction = stepInstruction;
    }
}
