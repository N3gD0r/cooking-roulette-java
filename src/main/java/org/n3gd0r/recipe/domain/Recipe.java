package org.n3gd0r.recipe.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.n3gd0r.recipe.domain.exception.RecipeIngredientNotFoundException;
import org.n3gd0r.recipe.domain.exception.RecipeInstructionNotFoundException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Recipe consists of: The name of the recipe; Ingredients, a set of
 * ingredients, with quantities, weight and more info about the ingredient; A
 * list of instructions, a step-by-step guide on how to cook the recipe, and
 * last: The estimated cooking time
 */
@Data
@NoArgsConstructor
@Entity
public class Recipe {
    @Id
    private UUID id;
    private UUID recipeUserId;
    @Column(length = 255, unique = true)
    private String name;
    private int cookTime;
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeInstruction> instructions;
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredient> ingredients;

    public Recipe(UUID id,
            String name,
            int cookTime,
            List<RecipeIngredient> ingredients,
            List<RecipeInstruction> instructions) {
        this.id = id;
        this.name = name.trim().toLowerCase();
        this.cookTime = cookTime;
        this.ingredients = new ArrayList<>(ingredients);
        this.instructions = new ArrayList<>(instructions);
    }

    public void setName(String name) {
        if (!name.isBlank() && !this.name.equalsIgnoreCase(name.trim())) {
            this.name = name.trim().toLowerCase();
        }
    }

    public void addInstruction(RecipeInstruction recipeInstruction) {
        instructions.add(recipeInstruction);
        recipeInstruction.setRecipe(this);
    }

    public void addIngredient(RecipeIngredient ingredient) {
        ingredients.add(ingredient);
        ingredient.setRecipe(this);
    }

    public boolean hasInstruction(UUID recipeInstructionId) {
        return instructions.stream().anyMatch(ri -> ri.getId().equals(recipeInstructionId));
    }

    public boolean hasIngredient(UUID ingredientId) {
        return ingredients.stream().anyMatch(ingredient -> ingredient.getId().equals(ingredientId));
    }

    public RecipeIngredient getIngredient(UUID ingredientId) {
        return ingredients.stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .findFirst()
                .orElseThrow(() -> new RecipeIngredientNotFoundException(ingredientId));
    }

    public RecipeInstruction getInstruction(UUID instructionId) {
        return instructions.stream()
                .filter(instruction -> instruction.getId().equals(instructionId))
                .findFirst()
                .orElseThrow(() -> new RecipeInstructionNotFoundException(instructionId));
    }

    public void setInstructions(List<RecipeInstruction> instructions) {
        this.instructions.clear();
        this.instructions.addAll(instructions);
        this.instructions.forEach(i -> i.setRecipe(this));
    }

    public void setIngredients(List<RecipeIngredient> ingredients) {
        this.ingredients.clear();
        this.ingredients.addAll(ingredients);
        this.ingredients.forEach(i -> i.setRecipe(this));
    }

    @Override
    public String toString() {
        return "Recipe [id=%s, name=%s]".formatted(id.toString(), name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Recipe other = (Recipe) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.getId()))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
}
