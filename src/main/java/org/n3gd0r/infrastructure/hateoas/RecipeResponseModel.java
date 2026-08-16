package org.n3gd0r.infrastructure.hateoas;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeIngredient;
import org.n3gd0r.recipe.domain.RecipeInstruction;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeResponseModel extends RepresentationModel<RecipeResponseModel> {
    private UUID id;
    private String name;
    private int cookTime;
    private List<IngredientResponseModel> ingredients;
    private List<RecipeInstructionResponseModel> instructions;

    public static RecipeResponseModel of(Recipe recipe) {
        return new RecipeResponseModel(
                recipe.getId().getId(),
                recipe.getName(),
                recipe.getCookTime(),
                recipe.getIngredients().stream().map(IngredientResponseModel::of).toList(),
                recipe.getInstructions().stream().map(RecipeInstructionResponseModel::of).toList());
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IngredientResponseModel {
        private UUID id;
        private String ingredientName;
        private IngredientEnum ingredientType;
        private int weightInGrams;

        public static IngredientResponseModel of(RecipeIngredient ingredient) {
            return new IngredientResponseModel(
                    ingredient.getId().getId(),
                    ingredient.getIngredientName(),
                    ingredient.getIngredientType(),
                    ingredient.getWeight().value());
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecipeInstructionResponseModel {
        private UUID id;
        private int instructionNumber;
        private String instruction;

        public static RecipeInstructionResponseModel of(RecipeInstruction recipeInstruction) {
            return new RecipeInstructionResponseModel(
                    recipeInstruction.getId().getId(),
                    recipeInstruction.getInstructionNumber(),
                    recipeInstruction.getInstruction());
        }
    }
}
