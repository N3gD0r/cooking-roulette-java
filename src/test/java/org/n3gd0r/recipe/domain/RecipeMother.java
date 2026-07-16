package org.n3gd0r.recipe.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class RecipeMother {

    public static Builder recipe() {
        return new Builder();
    }

    public static final class Builder {
        private RecipeId id = new RecipeId(UUID.randomUUID());
        private String name = "Huevos cocidos";
        private int cookTime = 20;
        private List<RecipeIngredient> ingredients = new ArrayList<>();
        private List<RecipeInstruction> instructions = new ArrayList<>();

        public Builder id(RecipeId id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder cookTime(int cookTime) {
            this.cookTime = cookTime;
            return this;
        }

        public Builder ingredients(List<RecipeIngredient> ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        public Builder instructions(List<RecipeInstruction> instructions) {
            this.instructions = instructions;
            return this;
        }

        public Builder withIngredient(RecipeIngredient ingredient) {
            ingredients.add(ingredient);
            return this;
        }

        public Builder withInstruction(RecipeInstruction instruction) {
            instructions.add(instruction);
            return this;
        }

        public Recipe build() {
            return new Recipe(id, name, cookTime, ingredients, instructions);
        }
    }
}
