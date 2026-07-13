package org.n3gd0r.roulette.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private Map<String, Object> toParams() {
        Map<String, Object> params = new HashMap<>();
        if (name != null && !name.trim().isEmpty()) {
            params.put("name", name.toLowerCase().trim());
        }
        if (cookTime != null && cookTime > 0) {
            params.put("cookTime", cookTime);
        }
        if (instructionQuantity != null && instructionQuantity > 0) {
            params.put("instructionQuantity", instructionQuantity);
        }
        if (ingredients != null) {
            params.put("ingredients", ingredients);
        }
        if (ingredientTypes != null) {
            params.put("ingredientTypes", ingredientTypes);
        }

        return params;
    }
}
