package org.n3gd0r.recipe.usecase;

import java.util.List;
import java.util.Optional;

import org.n3gd0r.infrastructure.mediator.Command;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeId;
import org.n3gd0r.recipe.usecase.records.PatchIngredientParameters;
import org.n3gd0r.recipe.usecase.records.PatchInstructionParameters;

public class PatchRecipeParameters extends Command<Recipe> {
    private final RecipeId recipeId;
    private final Optional<String> name;
    private final Optional<Integer> cookTime;
    private final Optional<List<PatchInstructionParameters>> instructions;
    private final Optional<List<PatchIngredientParameters>> ingredients;

    public PatchRecipeParameters(RecipeId recipeId,
            Optional<String> name,
            Optional<Integer> cookTime,
            Optional<List<PatchIngredientParameters>> ingredients,
            Optional<List<PatchInstructionParameters>> instructions) {
        this.recipeId = recipeId;
        this.name = name;
        this.cookTime = cookTime;
        this.instructions = instructions;
        this.ingredients = ingredients;
    }

    public RecipeId id() {
        return recipeId;
    }

    public Optional<List<PatchInstructionParameters>> instructions() {
        return instructions;
    }

    public Optional<List<PatchIngredientParameters>> ingredients() {
        return ingredients;
    }

    public Optional<String> name() {
        return name;
    }

    public Optional<Integer> cookTime() {
        return cookTime;
    }
}
