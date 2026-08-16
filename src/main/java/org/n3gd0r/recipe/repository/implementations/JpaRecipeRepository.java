package org.n3gd0r.recipe.repository.implementations;

import java.util.Optional;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeId;
import org.n3gd0r.recipe.domain.RecipeIngredientId;
import org.n3gd0r.recipe.domain.RecipeInstructionId;
import org.n3gd0r.recipe.domain.exception.RecipeNameIsEmptyException;
import org.n3gd0r.recipe.domain.exception.RecipeNotFoundException;
import org.n3gd0r.recipe.domain.exception.RecipeWithNameAlreadyExistsException;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.n3gd0r.recipe.repository.SpringDataJpaRecipeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * JpaRecipeRepository
 */
@Slf4j
@Repository
public class JpaRecipeRepository implements RecipeRepository {

    private final SpringDataJpaRecipeRepository repository;

    public JpaRecipeRepository(SpringDataJpaRecipeRepository repository) {
        this.repository = repository;
    }

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
        log.debug("Finding recipe by id: {}", id);
        return repository.findById(id);
    }

    @Override
    public Recipe findByName(String name) {
        log.debug("Finding recipe by name: {}", name);
        return repository.getRecipeByName(name).orElseThrow(() -> new RecipeNotFoundException(name));
    }

    @Override
    public void save(Recipe recipe) {
        log.debug("Saving recipe: {} ({})", recipe.getName(), recipe.getId());
        repository.save(recipe);
    }

    @Override
    public Recipe getById(RecipeId id) {
        log.debug("Getting recipe by id: {}", id);
        return repository.findById(id).orElseThrow(() -> new RecipeNotFoundException(id));
    }

    @Override
    public Page<Recipe> findAll(Pageable pageable) {
        log.debug("Finding all recipes with pageable: {}", pageable);
        return repository.findAll(pageable);
    }

    @Override
    public void validateExistsById(RecipeId recipeId) {
        if (!repository.existsById(recipeId)) {
            log.error("Recipe not found by id: {}", recipeId);
            throw new RecipeNotFoundException(recipeId);
        }
    }

    @Override
    public void validateNameUnique(String name) {
        if (name.isEmpty()) {
            log.error("Recipe name is empty");
            throw new RecipeNameIsEmptyException();
        }
        if (repository.existsByName(name)) {
            log.error("Recipe with name already exists: {}", name);
            throw new RecipeWithNameAlreadyExistsException(name);
        }
    }

    @Override
    public void deleteAll() {
        log.info("Deleting all recipes");
        repository.deleteAll();
    }

    @Override
    public void deleteById(RecipeId recipeId) {
        log.debug("Deleting recipe by id: {}", recipeId);
        repository.deleteById(recipeId);
    }

    @Override
    public long count() {
        return repository.count();
    }
}
