package org.n3gd0r.roulette.web;

import java.util.HashMap;
import java.util.Map;

import org.n3gd0r.roulette.usecase.RandomRecipeWithFilters;

public record RandomRecipeRequest(
        String name,
        Integer cookTime,
        String ingredientName,
        String ingredientType,
        Integer instructionQuantity,
        Integer pageSize) {

    public RandomRecipeWithFilters toQuery() {
        return new RandomRecipeWithFilters(pageSize, toParams());
    }

    private Map<String, String> toParams() {
        Map<String, String> params = new HashMap<>();
        if (name != null && !name.trim().isEmpty())
            params.put("name", name.trim());
        if (cookTime != null && !cookTime.toString().trim().isEmpty())
            params.put("cookTime", cookTime.toString().trim());
        if (ingredientName != null && !ingredientName.trim().isEmpty())
            params.put("ingredientName", ingredientName.trim());
        if (ingredientType != null && !ingredientType.trim().isEmpty())
            params.put("ingredientType", ingredientType.trim());
        if (instructionQuantity != null && !instructionQuantity.toString().trim().isEmpty())
            params.put("instructionQuantity", instructionQuantity.toString().trim());
        return params;
    }
}
