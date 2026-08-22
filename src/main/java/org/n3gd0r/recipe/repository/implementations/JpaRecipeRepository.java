package org.n3gd0r.recipe.repository.implementations;

import java.util.List;
import java.util.UUID;

import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.exception.RecipeNameIsEmptyException;
import org.n3gd0r.recipe.domain.exception.RecipeNotFoundException;
import org.n3gd0r.recipe.domain.exception.RecipeWithNameAlreadyExistsException;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.PredicateSpecification;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

/**
 * JpaRecipeRepository
 */
@Slf4j
@Repository
public class JpaRecipeRepository implements RecipeRepository {

    private final SpringJpaRepository repository;

    public JpaRecipeRepository(SpringJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public Recipe getById(UUID id) {
        log.debug("Getting recipe by id: {}", id);
        return repository.findById(id).orElseThrow(() -> new RecipeNotFoundException(id));
    }

    @Override
    public Recipe getByName(String name) {
        log.debug("Finding recipe by name: {}", name);
        return repository.getRecipeByName(name).orElseThrow(() -> new RecipeNotFoundException(name));
    }

    @Override
    public Page<Recipe> findAll(Pageable pageable) {
        log.debug("Finding all recipes with pageable: {}", pageable);
        return repository.findAll(pageable);
    }

    @Override
    public List<Recipe> findAll(PredicateSpecification<Recipe> spec) {
        return repository.findAll(spec);
    }

    @Override
    public void save(Recipe recipe) {
        log.debug("Saving recipe: {} ({})", recipe.getName(), recipe.getId());
        repository.save(recipe);
    }

    @Override
    public void deleteAll() {
        log.info("Deleting all recipes");
        repository.deleteAll();
    }

    @Override
    public void deleteById(UUID id) {
        log.debug("Deleting recipe by id: {}", id);
        repository.deleteById(id);
    }

    @Override
    public void validateExistsById(UUID id) {
        if (!repository.existsById(id)) {
            log.error("Recipe not found by id: {}", id);
            throw new RecipeNotFoundException(id);
        }
    }

    @Override
    public void validateNameUnique(String name) {
        if (repository.existsByName(name)) {
            log.error("Recipe with name already exists: {}", name);
            throw new RecipeWithNameAlreadyExistsException(name);
        }
    }

    @Override
    public void validateEmptyName(String name) {
        if (name.isEmpty()) {
            log.error("Recipe name is empty");
            throw new RecipeNameIsEmptyException();
        }
    }

    // TODO: generate UUIDv7
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
}
