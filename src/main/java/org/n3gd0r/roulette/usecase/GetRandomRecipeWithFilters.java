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

    /**
     * Filter recipes by requested parameters that can be recipe name,
     * cook time, ingreident name and/or ingredient type.
     */

    /*
     * TODO properly filter recipes using pagination
     * 1. need to get all recipes to filter them
     * 2. Loading all recipes can memory expensive
     * 3. need to fo batch/pagination with findAll(pageable)
     * 4. need to find tolerable page sizes
     * 5. only accesible information is the total recipes count
     * 6. need to calculate proper batches sizes from total recipes count
     * 7. then filter each batch until we find a list of recipes or a recipe that
     * matches the requested parameters
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

    private List<Predicate<Recipe>> getPredicates(Map<String, String> params) {
        List<Predicate<Recipe>> recipePredicates = new ArrayList<>();

        params.forEach((k, v) -> {
            if (k.equals("ingredientName")) {
                Predicate<Recipe> ingredientName = r -> r.getIngredients().stream()
                        .filter(i -> i.getIngredientName().toLowerCase().equals(v.toLowerCase()))
                        .count() > 0;
                recipePredicates.add(ingredientName);
            }
            if (k.equals("ingredientType")) {
                Predicate<Recipe> ingredientType = r -> r.getIngredients().stream()
                        .filter(i -> i.getIngredientType().toString().toLowerCase().equals(v.toLowerCase()))
                        .count() > 0;
                recipePredicates.add(ingredientType);
            }
            if (k.equals("instructionQuantity")) {
                Predicate<Recipe> instructionQuantity = r -> r.getInstructions().size() == Integer.parseInt(v);
                recipePredicates.add(instructionQuantity);
            }

            if (k.equals("cookTime")) {
                Predicate<Recipe> cookTime = r -> r.getCookTime() == Integer.parseInt(v);
                recipePredicates.add(cookTime);
            }

            if (k.equals("name")) {
                Predicate<Recipe> recipeName = r -> r.getName().toLowerCase().contains(v.toLowerCase());
                recipePredicates.add(recipeName);
            }
        });
        return recipePredicates;
    }
}
