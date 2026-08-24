package org.n3gd0r.recipe.repository.implementations;

import java.util.List;

import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeIngredient;
import org.n3gd0r.recipe.domain.RecipeInstruction;
import org.n3gd0r.recipe.repository.FilterQuery;
import org.springframework.data.jpa.domain.PredicateSpecification;

public final class FilterRecipeSpecification {
    public static PredicateSpecification<Recipe> hasName(String name) {
        return (root, builder) -> builder.equal(root.<String>get("name"), name);
    }

    public static PredicateSpecification<Recipe> hasCookTime(int cookTime) {
        return (root, builder) -> builder.equal(root.get("cookTime"), cookTime);
    }

    public static PredicateSpecification<Recipe> hasInstructionSize(int quantity) {
        return (root, builder) -> builder.equal(
                builder.size(root.<List<RecipeInstruction>>get("instructions")),
                quantity);
    }

    public static PredicateSpecification<Recipe> hasIngredientSize(int quantity) {
        return (root, builder) -> builder.equal(
                builder.size(root.<List<RecipeIngredient>>get("ingredients")),
                quantity);
    }

    public static PredicateSpecification<Recipe> hasIngredients(List<String> ingredients) {
        return (root, builder) -> root
                .<Recipe, RecipeIngredient>join("ingredients")
                .<String>get("ingredientName")
                .in(ingredients);
    }

    public static PredicateSpecification<Recipe> hasIngredientTypes(List<IngredientEnum> ingredientTypes) {
        return (root, builder) -> root
                .<Recipe, RecipeIngredient>join("ingredients")
                .<IngredientEnum>get("ingredientType")
                .in(ingredientTypes);
    }

    public static PredicateSpecification<Recipe> buildSpecification(FilterQuery filters) {
        PredicateSpecification<Recipe> spec = PredicateSpecification.unrestricted();
        filters.name().ifPresent(name -> spec.and(hasName(name)));
        filters.cookTime().ifPresent(cookTime -> spec.and(hasCookTime(cookTime)));
        filters.instructionQuantity().ifPresent(quantity -> spec.and(hasInstructionSize(quantity)));
        filters.ingredientQuantity().ifPresent(quantity -> spec.and(hasIngredientSize(quantity)));
        filters.ingredients().ifPresent(ingredients -> spec.and(hasIngredients(ingredients)));
        filters.ingredientTypes().ifPresent(ingredientTypes -> spec.and(hasIngredientTypes(ingredientTypes)));
        return spec;
    }
}
