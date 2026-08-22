package org.n3gd0r.recipe.repository;

import java.util.UUID;

public interface RecipeIdentifiersGenerator {
    UUID nextId();

    UUID nextRecipeIngredientId();

    UUID nextRecipeInstructionId();
}
