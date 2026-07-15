package org.n3gd0r.recipe.repository;

import java.util.Optional;
import java.util.UUID;

import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeId;
import org.n3gd0r.recipe.domain.RecipeIngredientId;
import org.n3gd0r.recipe.domain.RecipeInstructionId;
import org.n3gd0r.recipe.domain.exception.RecipeNotFoundException;
import org.n3gd0r.recipe.domain.exception.RecipeWithNameAlreadyExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * JpaRecipeRepository
 */
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
        return repository.findById(id);
    }

    @Override
    public Recipe findByName(String name) {
        return repository.getRecipeByName(name).orElseThrow(() -> new RecipeNotFoundException(name));
    }

    @Override
    public void save(Recipe recipe) {
        repository.save(recipe);
    }

    @Override
    public Recipe getById(RecipeId id) {
        return repository.findById(id).orElseThrow(() -> new RecipeNotFoundException(id));
    }

    @Override
    public Page<Recipe> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public void validateExistsById(RecipeId recipeId) {
        if (!repository.existsById(recipeId)) {
            throw new RecipeNotFoundException(recipeId);
        }
    }

    @Override
    public void validateNameUnique(String name) {
        if (repository.existsByName(name)) {
            throw new RecipeWithNameAlreadyExistsException(name);
        }
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public void deleteById(RecipeId recipeId) {
        repository.deleteById(recipeId);
    }

    @Override
    public long count() {
        return repository.count();
    }
}
