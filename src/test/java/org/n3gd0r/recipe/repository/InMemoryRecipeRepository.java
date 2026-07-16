package org.n3gd0r.recipe.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeId;
import org.n3gd0r.recipe.domain.RecipeIngredientId;
import org.n3gd0r.recipe.domain.RecipeInstructionId;
import org.n3gd0r.recipe.domain.exception.RecipeNotFoundException;
import org.n3gd0r.recipe.domain.exception.RecipeWithNameAlreadyExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class InMemoryRecipeRepository implements RecipeRepository {
    private final Map<RecipeId, Recipe> values = new HashMap<>();

    @Override
    public RecipeId nextId() {
        return new RecipeId(UUID.randomUUID());
    }

    @Override
    public RecipeIngredientId nextRecipeIngredientId() {
        return new RecipeIngredientId(UUID.randomUUID());
    }

    @Override
    public RecipeInstructionId nextRecipeInstructionId() {
        return new RecipeInstructionId(UUID.randomUUID());
    }

    @Override
    public Optional<Recipe> findById(RecipeId id) {
        return Optional.ofNullable(values.get(id));
    }

    @Override
    public Recipe findByName(String name) {
        return values.values().stream().filter(r -> r.getName().equalsIgnoreCase(name.trim().toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new RecipeNotFoundException(name));
    }

    @Override
    public void save(Recipe recipe) {
        values.put(recipe.getId(), recipe);
    }

    @Override
    public Recipe getById(RecipeId id) {
        return findById(id).orElseThrow(() -> new RecipeNotFoundException(id));
    }

    @Override
    public void validateExistsById(RecipeId recipeId) {
        if (!values.containsKey(recipeId)) {
            throw new RecipeNotFoundException(recipeId);
        }
    }

    @Override
    public void validateNameUnique(String name) {
        boolean nameExists = values.values().stream()
                .filter(r -> r.getName().equalsIgnoreCase(name.trim().toLowerCase()))
                .count() > 0;
        if (nameExists) {
            throw new RecipeWithNameAlreadyExistsException(name);
        }
    }

    @Override
    public Page<Recipe> findAll(Pageable pageable) {
        List<Recipe> recipes = values.values().stream()
                .skip((long) pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .toList();
        return new PageImpl<>(recipes, pageable, values.size());
    }

    @Override
    public void deleteAll() {
        values.clear();
    }

    @Override
    public void deleteById(RecipeId recipeId) {
        values.remove(recipeId);
    }

    @Override
    public long count() {
        return values.size();
    }

}
