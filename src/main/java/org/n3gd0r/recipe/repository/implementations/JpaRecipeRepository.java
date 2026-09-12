package org.n3gd0r.recipe.repository.implementations;

import static org.n3gd0r.recipe.repository.implementations.FilterRecipeSpecification.buildSpecification;

import java.util.List;
import java.util.UUID;

import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.exception.RecipeNotFoundException;
import org.n3gd0r.recipe.domain.exception.RecipeWithNameAlreadyExistsException;
import org.n3gd0r.recipe.repository.FilterQuery;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.NoArgGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class JpaRecipeRepository implements RecipeRepository {

    private final SpringJpaRepository repository;
    private static final NoArgGenerator ID_GENERATOR = Generators.timeBasedEpochGenerator();

    public JpaRecipeRepository(SpringJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public long count(UUID recipeUserId) {
        return repository.countByRecipeUserId(recipeUserId);
    }

    @Override
    public Recipe getById(UUID recipeUserId, UUID id) {
        log.debug("Getting recipe by id: {}", id);
        return repository.findByRecipeUserIdAndId(recipeUserId, id).orElseThrow(() -> new RecipeNotFoundException(id));
    }

    @Override
    public Recipe getByName(UUID recipeUserId, String name) {
        log.debug("Finding recipe by name: {}", name);
        return repository.findByRecipeUserIdAndName(recipeUserId, name)
                .orElseThrow(() -> new RecipeNotFoundException(name));
    }

    @Override
    public Page<Recipe> findAll(UUID recipeUserId, Pageable pageable) {
        log.debug("Finding all recipes with pageable: {}", pageable);
        return repository.findAllByRecipeUserId(recipeUserId, pageable);
    }

    @Override
    public List<Recipe> findAll(UUID recipeUserId, FilterQuery filters) {
        if (filters.noFilters()) {
            throw new ZeroFiltersForQueryException();
        }
        return repository.findAllByRecipeUserId(recipeUserId, buildSpecification(recipeUserId, filters));
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
    public void deleteById(UUID recipeUserId, UUID id) {
        log.debug("Deleting recipe by id: {}", id);
        Recipe recipe = repository.findByRecipeUserIdAndId(recipeUserId, id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
        repository.delete(recipe);
    }

    @Override
    public void validateExistsById(UUID recipeUserId, UUID id) {
        if (!repository.existsByRecipeUserIdAndId(recipeUserId, id)) {
            log.error("Recipe not found by id: {} for user: {}", id, recipeUserId);
            throw new RecipeNotFoundException(id);
        }
    }

    @Override
    public void validateNameUnique(UUID recipeUserId, String name) {
        if (repository.existsByRecipeUserIdAndName(recipeUserId, name.trim().toLowerCase())) {
            log.error("Recipe with name already exists: {}", name);
            throw new RecipeWithNameAlreadyExistsException(name);
        }
    }

    @Override
    public UUID nextId() {
        return ID_GENERATOR.generate();
    }

    @Override
    public UUID nextRecipeIngredientId() {
        return ID_GENERATOR.generate();
    }

    @Override
    public UUID nextRecipeInstructionId() {
        return ID_GENERATOR.generate();
    }
}
