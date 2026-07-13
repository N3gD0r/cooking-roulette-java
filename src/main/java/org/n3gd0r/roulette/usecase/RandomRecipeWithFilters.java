package org.n3gd0r.roulette.usecase;

import java.util.Map;

import org.n3gd0r.infrastructure.mediator.Query;
import org.n3gd0r.recipe.domain.Recipe;

public class RandomRecipeWithFilters extends Query<Recipe> {
    private final int pageSize;
    private final Map<String, Object> params;

    public RandomRecipeWithFilters(int pageSize, Map<String, Object> params) {
        this.params = params;
        this.pageSize = pageSize;
    }

    public Map<String, Object> params() {
        return params;
    }

    public boolean empty() {
        return params.isEmpty();
    }

    public int pageSize() {
        return pageSize;
    }
}
