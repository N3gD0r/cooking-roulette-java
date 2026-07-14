package org.n3gd0r.roulette.usecase;

import java.util.List;
import java.util.Optional;

import org.n3gd0r.infrastructure.mediator.Query;
import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Recipe;

public class RandomRecipeFiltersParameters extends Query<Recipe> {
    private final int pageSize;
    private Optional<String> name;
    private Optional<Integer> cookTime;
    private Optional<Integer> instructionQuantity;
    private Optional<Integer> ingredientQuantity;
    private Optional<List<String>> ingredients;
    private Optional<List<IngredientEnum>> ingredientTypes;

    public RandomRecipeFiltersParameters(int pageSize,
            Optional<String> name,
            Optional<Integer> cookTime,
            Optional<Integer> instructionQuantity,
            Optional<Integer> ingredientQuantity,
            Optional<List<String>> ingredients,
            Optional<List<IngredientEnum>> ingredientTypes) {
        this.pageSize = pageSize;
        this.name = name;
        this.cookTime = cookTime;
        this.instructionQuantity = instructionQuantity;
        this.ingredientQuantity = ingredientQuantity;
        this.ingredients = ingredients;
        this.ingredientTypes = ingredientTypes;

    }

    public int pageSize() {
        return pageSize;
    }

    public Optional<String> name() {
        return name;
    }

    public Optional<Integer> cookTime() {
        return cookTime;
    }

    public Optional<Integer> instructionQuantity() {
        return instructionQuantity;
    }

    public Optional<Integer> ingredientQuantity() {
        return ingredientQuantity;
    }

    public Optional<List<String>> ingredients() {
        return ingredients;
    }

    public Optional<List<IngredientEnum>> ingredientTypes() {
        return ingredientTypes;
    }

}
