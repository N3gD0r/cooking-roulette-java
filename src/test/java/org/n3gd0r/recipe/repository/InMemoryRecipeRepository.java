package org.n3gd0r.recipe.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.exception.RecipeNameIsEmptyException;
import org.n3gd0r.recipe.domain.exception.RecipeNotFoundException;
import org.n3gd0r.recipe.domain.exception.RecipeWithNameAlreadyExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.PredicateSpecification;

public class InMemoryRecipeRepository implements RecipeRepository {
    private final Map<UUID, Recipe> values = new HashMap<>();

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
        return values.values().stream().filter(r -> r.getName().equalsIgnoreCase(name.trim().toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new RecipeNotFoundException(name));
    }

    @Override
    public void save(Recipe recipe) {
        values.put(recipe.getId(), recipe);
    }

    @Override
    public Recipe getById(UUID id) {
        return Optional.ofNullable(values.get(id)).orElseThrow(() -> new RecipeNotFoundException(id));
    }

    @Override
    public void validateExistsById(UUID id) {
        if (!values.containsKey(id)) {
            throw new RecipeNotFoundException(id);
        }
    }

    @Override
    public void validateNameUnique(String name) {
        boolean nameExists = values.values().stream()
                .filter(r -> r.getName().equalsIgnoreCase(name.trim().toLowerCase())).count() > 0;
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
    public void deleteById(UUID id) {
        values.remove(id);
    }

    @Override
    public long count() {
        return values.size();
    }

    @Override
    public List<Recipe> findAll(PredicateSpecification<Recipe> spec) {
        return values.values().stream().toList();
    }

    @Override
    public void validateEmptyName(String name) {
        if (name.isEmpty()) {
            throw new RecipeNameIsEmptyException();
        }
    }
}
