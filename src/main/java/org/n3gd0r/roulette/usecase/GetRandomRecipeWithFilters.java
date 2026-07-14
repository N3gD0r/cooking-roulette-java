package org.n3gd0r.roulette.usecase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;

import org.n3gd0r.infrastructure.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.n3gd0r.roulette.domain.exception.NoRecipesFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class GetRandomRecipeWithFilters implements RequestHandler<RandomRecipeWithFilters, Recipe> {
    private final RecipeRepository repository;

    public GetRandomRecipeWithFilters(RecipeRepository repository) {
        this.repository = repository;
    }

    // TODO filter by ingredients quantity
    /**
     * Filter recipes by requested parameters that can be recipe name,
     * cook time, instructions quantity, ingredients name and/or ingredients type.
     */
    @Override
    public Recipe execute(RandomRecipeWithFilters request) {
        long totalRecipes = repository.count();

        if (totalRecipes < 1) {
            new NoRecipesFoundException();
        }

        if (totalRecipes == 1) {
            return repository.findAll(PageRequest.of(0, 1)).stream().findAny()
                    .orElseThrow(NoRecipesFoundException::new);
        }

        List<Recipe> recipes = new ArrayList<>();

        int totalPages = totalRecipes < request.pageSize() ? 1
                : (int) Math.ceil((double) totalRecipes / request.pageSize());

        for (int i = 0; i < totalPages; i++) {
            recipes.addAll(repository.findAll(PageRequest.of(i * request.pageSize(), request.pageSize()))
                    .stream()
                    .filter(getPredicates(request.params()).stream()
                            .reduce(Predicate::and).orElse(r -> false))
                    .toList());
        }

        totalRecipes = recipes.size();
        int randomPage = new Random().nextInt((int) totalRecipes);

        Recipe foundRecipe = recipes.stream()
                .skip(randomPage)
                .findFirst()
                .orElseThrow(NoRecipesFoundException::new);

        return foundRecipe;
    }

    private List<Predicate<Recipe>> getPredicates(Map<String, Object> params) {
        List<Predicate<Recipe>> recipePredicates = new ArrayList<>();

        params.forEach((k, v) -> {
            if (k.equals("ingredients") && v instanceof List<?> ingredients) {
                for (int i = 0; i < ingredients.size(); i++) {
                    if (ingredients.get(i) instanceof String ingredient) {
                        Predicate<Recipe> ingredientPredicate = r -> r.getIngredients().stream()
                                .filter(ri -> ri.getIngredientName()
                                        .equalsIgnoreCase(ingredient))
                                .count() > 0;
                        recipePredicates.add(ingredientPredicate);
                    }
                }
            }
            if (k.equals("ingredientTypes") && v instanceof List<?> ingredientTypes) {
                for (int i = 0; i < ingredientTypes.size(); i++) {
                    if (ingredientTypes.get(i) instanceof String ingredientType) {
                        Predicate<Recipe> ingredientTypePredicate = r -> r.getIngredients().stream()
                                .filter(ri -> ri.getIngredientType().toString()
                                        .equalsIgnoreCase(ingredientType))
                                .count() > 0;
                        recipePredicates.add(ingredientTypePredicate);
                    }
                }
            }
            if (k.equals("instructionQuantity") && v instanceof Integer instructionQuantity) {
                Predicate<Recipe> instructionQuantityPredicate = r -> r.getInstructions().size() == instructionQuantity;
                recipePredicates.add(instructionQuantityPredicate);
            }

            if (k.equals("cookTime") && v instanceof Integer cookTime) {
                Predicate<Recipe> cookTimePredicate = r -> r.getCookTime() == cookTime;
                recipePredicates.add(cookTimePredicate);
            }

            if (k.equals("name") && v instanceof String recipeName) {
                Predicate<Recipe> recipeNamePredicate = r -> r.getName().toLowerCase()
                        .contains(recipeName);
                recipePredicates.add(recipeNamePredicate);
            }
        });
        return recipePredicates;
    }
}
