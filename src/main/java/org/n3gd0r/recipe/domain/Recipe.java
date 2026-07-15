package org.n3gd0r.recipe.domain;

import java.util.ArrayList;
import java.util.List;

import org.n3gd0r.commons.AbstractEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

/**
 * A Recipe consists of: The name of the recipe; Ingredients, a set of
 * ingredients, with quantities, weight and more info about the ingredient; A
 * list of instructions, a step-by-step guide on how to cook the recipe, and
 * last: The estimated cooking time
 */
@Entity
public class Recipe extends AbstractEntity<RecipeId> {
    @Column(length = 255, unique = true)
    private String name;
    private int cookTime;
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeInstruction> instructions = new ArrayList<>();
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredient> ingredients = new ArrayList<>();

    protected Recipe() {
    }

    public Recipe(RecipeId id,
            String name,
            int cookTime,
            List<RecipeIngredient> ingredients,
            List<RecipeInstruction> instructions) {
        super(id);
        this.name = name;
        this.cookTime = cookTime;
        for (RecipeIngredient ingredientInfo : ingredients) {
            addIngredient(ingredientInfo);
        }
        for (RecipeInstruction instruction : instructions) {
            addInstruction(instruction);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public List<RecipeInstruction> getInstructions() {
        return instructions;
    }

    public void addInstruction(RecipeInstruction recipeInstruction) {
        instructions.add(recipeInstruction);
        recipeInstruction.setRecipe(this);
    }

    public boolean hasInstruction(RecipeInstructionId recipeInstructionId) {
        return instructions.stream().anyMatch(ri -> ri.getId().equals(recipeInstructionId));
    }

    public void addIngredient(RecipeIngredient ingredient) {
        ingredients.add(ingredient);
        ingredient.setRecipe(this);
    }

    public boolean hasIngredient(RecipeIngredientId ingredientId) {
        return ingredients.stream().anyMatch(ingredient -> ingredient.getId().equals(ingredientId));
    }

    public void setInstructions(List<RecipeInstruction> instructions) {
        this.instructions = instructions;
        this.instructions.forEach(i -> i.setRecipe(this));
    }

    public void setIngredients(List<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
        this.ingredients.forEach(i -> i.setRecipe(this));
    }
}
