package org.n3gd0r.recipe.domain;

import java.util.UUID;

public final class RecipeIngredientMother {
    public static Builder recipeIngredient() {
        return new Builder();
    }

    public static final class Builder {
        private UUID id = UUID.randomUUID();
        private String ingredientName = "agua";
        private IngredientEnum ingredientType = IngredientEnum.ALIMENTOS_LIBRES_DE_ENERGIA;
        private Mass weight = Mass.ofGrams(250);

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder ingredientName(String ingredientName) {
            this.ingredientName = ingredientName;
            return this;
        }

        public Builder ingredientType(IngredientEnum ingredientType) {
            this.ingredientType = ingredientType;
            return this;
        }

        public Builder weight(Mass weight) {
            this.weight = weight;
            return this;
        }

        public RecipeIngredient build() {
            return new RecipeIngredient(id, ingredientName, ingredientType, weight);
        }
    }

}
