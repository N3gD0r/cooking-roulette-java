package org.n3gd0r.recipe.domain;

import jakarta.persistence.*;
import org.n3gd0r.commons.AbstractEntity;

@Entity
public class RecipeIngredient extends AbstractEntity<RecipeIngredientId> {
    @Column(length = 255)
    private String ingredientName;
    @Enumerated(EnumType.STRING)
    private IngredientEnum ingredientType;
    private Mass weight;
    @ManyToOne(fetch = FetchType.LAZY)
    private Recipe recipe;

    protected RecipeIngredient() {
    }

    public RecipeIngredient(RecipeIngredientId id, String ingredientName, IngredientEnum ingredientType, Mass weight) {
        super(id);
        this.ingredientName = ingredientName;
        this.ingredientType = ingredientType;
        this.weight = weight;
    }

    public Mass getWeight() {
        return weight;
    }

    public void setWeight(Mass weight) {
        this.weight = weight;
    }

    public float totalMass() {
        return weight.value();
    }

    public IngredientEnum getIngredientType() {
        return ingredientType;
    }

    public void setIngredientType(IngredientEnum ingredientType) {
        this.ingredientType = ingredientType;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
