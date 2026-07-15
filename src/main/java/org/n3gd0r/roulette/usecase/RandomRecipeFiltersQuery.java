package org.n3gd0r.roulette.usecase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import org.n3gd0r.infrastructure.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.n3gd0r.roulette.domain.exception.NoRecipesFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class RandomRecipeFiltersQuery implements RequestHandler<RandomRecipeFiltersParameters, Recipe> {
    private final RecipeRepository repository;

    public RandomRecipeFiltersQuery(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Recipe execute(RandomRecipeFiltersParameters request) {
        long totalRecipes = repository.count();
        if (totalRecipes < 1) {
            throw new NoRecipesFoundException();
        }
        if (totalRecipes == 1) {
            return repository.findAll(PageRequest.of(0, 1)).stream()
                    .findFirst()
                    .orElseThrow(NoRecipesFoundException::new);
        }

        int totalPages = totalRecipes < request.pageSize() ? 1
                : (int) Math.ceil((double) totalRecipes / request.pageSize());
        List<Recipe> recipes = new ArrayList<>();

        for (int i = 0; i < totalPages; i++) {
            int currentPage = i * request.pageSize();
            recipes.addAll(repository.findAll(PageRequest.of(currentPage, request.pageSize()))
                    .stream()
                    .filter(getPredicates(request).stream()
                            .reduce(Predicate::and).orElse(r -> false))
                    .toList());
        }

        long foundRecipes = recipes.size();

        if (foundRecipes < 1) {
            throw new NoRecipesFoundException();
        }
        if (foundRecipes == 1) {
            return recipes.getFirst();
        }

        int randomPage = new Random().nextInt((int) foundRecipes);
        Recipe randomRecipe = recipes.stream()
                .skip(randomPage)
                .findFirst()
                .orElseThrow(NoRecipesFoundException::new);

        return randomRecipe;
    }

    private List<Predicate<Recipe>> getPredicates(RandomRecipeFiltersParameters request) {
        List<Predicate<Recipe>> recipePredicates = new ArrayList<>();

        if (request.ingredients().isPresent()) {
            for (String ingredient : request.ingredients().get()) {
                recipePredicates.add(r -> r.getIngredients().stream()
                        .filter(ri -> ri.getIngredientName().equalsIgnoreCase(ingredient)).count() > 0);
            }
        }

        if (request.ingredientTypes().isPresent()) {
            for (IngredientEnum ingredientType : request.ingredientTypes().get()) {
                recipePredicates.add(r -> r.getIngredients().stream()
                        .filter(ri -> ri.getIngredientType().toString()
                                .equalsIgnoreCase(ingredientType.toString()))
                        .count() > 0);
            }
        }

        if (request.ingredientQuantity().isPresent()) {
            recipePredicates.add(r -> r.getIngredients().size() == request.ingredientQuantity().get());
        }

        if (request.instructionQuantity().isPresent()) {
            recipePredicates.add(r -> r.getInstructions().size() == request.instructionQuantity().get());
        }

        if (request.cookTime().isPresent()) {
            recipePredicates.add(r -> r.getCookTime() == request.cookTime().get());
        }

        if (request.name().isPresent()) {
            recipePredicates.add(r -> r.getName().equalsIgnoreCase(request.name().get()));
        }

        return recipePredicates;
    }
}
