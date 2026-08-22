package org.n3gd0r.recipe.web;

import java.util.List;
import java.util.Optional;

import org.n3gd0r.recipe.domain.IngredientEnum;

public record SearchRecipeRequest(
        Optional<String> name,
        Optional<Integer> cookTime,
        Optional<Integer> instructionQuantity,
        Optional<Integer> ingredientQuantity,
        Optional<List<String>> ingredients,
        Optional<List<IngredientEnum>> ingredientTypes) {
}
