package org.n3gd0r.recipe.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.exception.RecipeNotFoundException;
import org.n3gd0r.recipe.domain.exception.RecipeWithNameAlreadyExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class InMemoryRecipeRepository implements RecipeRepository {
    private final Map<UUID, Recipe> recipeTable = new HashMap<>();

    @Override
    public UUID nextId() {
        return UUID.randomUUID();
    }

    @Override
    public UUID nextRecipeIngredientId() {
        return UUID.randomUUID();
    }

    @Override
    public UUID nextRecipeInstructionId() {
        return UUID.randomUUID();
    }

    @Override
    public Recipe getByName(String name) {
        return recipeTable.values().stream().filter(r -> r.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new RecipeNotFoundException(name));
    }

    @Override
    public void save(Recipe recipe) {
        recipeTable.put(recipe.getId(), recipe);
    }

    @Override
    public Recipe getById(UUID id) {
        return Optional.ofNullable(recipeTable.get(id)).orElseThrow(() -> new RecipeNotFoundException(id));
    }

    @Override
    public void validateExistsById(UUID id) {
        if (!recipeTable.containsKey(id)) {
            throw new RecipeNotFoundException(id);
        }
    }

    @Override
    public void validateNameUnique(String name) {
        boolean exists = recipeTable.values().stream()
                .anyMatch(r -> r.getName().equalsIgnoreCase(name));
        if (exists) {
            throw new RecipeWithNameAlreadyExistsException(name);
        }
    }

    @Override
    public Page<Recipe> findAll(Pageable pageable) {
        List<Recipe> recipes = recipeTable.values().stream()
                .skip((long) pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .toList();
        return new PageImpl<>(recipes, pageable, recipeTable.size());
    }

    @Override
    public void deleteAll() {
        recipeTable.clear();
    }

    @Override
    public void deleteById(UUID id) {
        recipeTable.remove(id);
    }

    @Override
    public long count() {
        return recipeTable.size();
    }

    @Override
    public List<Recipe> findAll(FilterQuery filters) {
        List<Recipe> recipes = recipeTable.values().stream()
                .filter(r -> r.getName().equalsIgnoreCase(filters.name().get()))
                .toList();
        return recipes;
    }
}
