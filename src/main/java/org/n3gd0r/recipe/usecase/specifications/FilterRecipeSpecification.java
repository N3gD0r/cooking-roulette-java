package org.n3gd0r.recipe.usecase.specifications;

import org.n3gd0r.recipe.domain.Recipe;
import org.springframework.data.jpa.domain.PredicateSpecification;

public final class FilterRecipeSpecification {
    public static PredicateSpecification<Recipe> hasName(String name) {
        return (root, builder) -> builder.equal(root.<String>get("name"), name);
    }

    public static PredicateSpecification<Recipe> hasCookTime(int cookTime) {
        return (root, builder) -> builder.equal(root.<Integer>get("cookTime"), (Integer) cookTime);
    }
}
