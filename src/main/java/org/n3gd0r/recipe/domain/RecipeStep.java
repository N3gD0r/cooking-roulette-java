package org.n3gd0r.recipe.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import org.n3gd0r.commons.AbstractEntity;

@Entity
public class RecipeStep extends AbstractEntity<RecipeStepId> {
    private int stepNumber;
    private String stepInstruction;
    @ManyToOne(fetch = FetchType.LAZY)
    private Recipe recipe;

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

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
