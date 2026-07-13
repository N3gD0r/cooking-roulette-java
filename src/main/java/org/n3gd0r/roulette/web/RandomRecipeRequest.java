package org.n3gd0r.roulette.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.n3gd0r.roulette.usecase.RandomRecipeWithFilters;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

public record RandomRecipeRequest(
        @Valid @Positive Integer pageSize,
        String name,
        Integer cookTime,
        Integer instructionQuantity,
        List<String> ingredients,
        List<String> ingredientTypes) {

    public RandomRecipeWithFilters toQuery() {
        return new RandomRecipeWithFilters(pageSize, toParams());
    }

    private Map<String, String> toParams() {
        Map<String, String> params = new HashMap<>();
        if (name != null && !name.trim().isEmpty())
            params.put("name", name.trim());
        if (cookTime != null && !cookTime.toString().trim().isEmpty())
            params.put("cookTime", cookTime.toString().trim());
        if (instructionQuantity != null && !instructionQuantity.toString().trim().isEmpty())
            params.put("instructionQuantity", instructionQuantity.toString().trim());

        if (ingredients != null) {
            String ingredientsString = listToString(ingredients);
            params.put("ingredients", ingredientsString.trim());
        }
        if (ingredientTypes != null) {
            String ingredientTypeString = listToString(ingredientTypes);
            params.put("ingredientTypes", ingredientTypeString.trim());
        }

        return params;
    }

    private String listToString(List<String> list) {
        return list.stream().collect(Collectors.joining(" "));
    }
}
