package org.n3gd0r.recipe.domain;

import org.n3gd0r.commons.AbstractEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

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

    public int totalMass() {
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
