package org.n3gd0r.recipe.usecase.get;

import org.n3gd0r.commons.mediator.Query;
import org.n3gd0r.recipe.domain.Recipe;
import org.springframework.data.domain.Page;

public record GetAllRecipesParameters(int page, int size) implements Query<Page<Recipe>> {
    public static GetAllRecipesParameters defaultPage() {
        return new GetAllRecipesParameters(0, 50);
    }
}
