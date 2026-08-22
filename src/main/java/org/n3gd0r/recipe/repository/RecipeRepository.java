package org.n3gd0r.recipe.repository;

public interface RecipeRepository
        extends WriteRecipeRepository, ReadRecipeRepository, RecipeIdentifiersGenerator, ValidationRecipeRepository {
}
