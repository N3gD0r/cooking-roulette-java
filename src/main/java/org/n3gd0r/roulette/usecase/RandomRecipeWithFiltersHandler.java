package org.n3gd0r.roulette.usecase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

import org.n3gd0r.commons.mediator.HandlerFor;
import org.n3gd0r.commons.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.n3gd0r.roulette.domain.exception.NoRecipesFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@HandlerFor(RandomRecipeFiltersParameters.class)
@Service
@Transactional(readOnly = true)
public class RandomRecipeWithFiltersHandler implements RequestHandler<RandomRecipeFiltersParameters, Recipe> {
    private final RecipeRepository repository;

    public RandomRecipeWithFiltersHandler(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Recipe execute(RandomRecipeFiltersParameters request) {
        log.info("Getting random recipe with filters");
        long totalRecipes = repository.count();
        log.debug("Total recipes in database: {}", totalRecipes);
        if (totalRecipes < 1) {
            log.warn("No recipes found in database");
            throw new NoRecipesFoundException();
        }

        if (request.isEmptyRequest()) {
            log.debug("Empty filter request, selecting random recipe from all");
            int randomPage = ThreadLocalRandom.current().nextInt((int) totalRecipes);
            Recipe foundRecipe = repository.findAll(PageRequest.of(randomPage, 1)).stream()
                    .findFirst()
                    .orElseThrow(NoRecipesFoundException::new);
            return foundRecipe;
        }

        int totalPages = totalRecipes < request.pageSize() ? 1
                : (int) Math.ceil((double) totalRecipes / request.pageSize());
        log.debug("Scanning {} pages with pageSize: {}", totalPages, request.pageSize());
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
        log.debug("Found {} recipes matching filters out of {} total", foundRecipes, totalRecipes);

        if (foundRecipes < 1) {
            log.warn("No recipes matched the given filters");
            throw new NoRecipesFoundException();
        }
        if (foundRecipes == 1) {
            log.debug("Only one recipe matched filters, returning it");
            return recipes.getFirst();
        }

        int randomIndex = ThreadLocalRandom.current().nextInt((int) foundRecipes);
        Recipe randomRecipe = recipes.get(randomIndex);

        log.info("Random recipe selected with filters: {} ({})", randomRecipe.getName(), randomRecipe.getId());
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
