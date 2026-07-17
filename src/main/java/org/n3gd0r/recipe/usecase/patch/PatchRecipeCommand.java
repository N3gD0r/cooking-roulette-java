package org.n3gd0r.recipe.usecase.patch;

import org.n3gd0r.infrastructure.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeIngredient;
import org.n3gd0r.recipe.domain.RecipeInstruction;
import org.n3gd0r.recipe.domain.exception.EntityNotSuitableForUpdateException;
import org.n3gd0r.recipe.domain.exception.RecipeIngredientNotFoundException;
import org.n3gd0r.recipe.domain.exception.RecipeInstructionNotFoundException;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PatchRecipeCommand implements RequestHandler<PatchRecipeParameters, Recipe> {
    private final RecipeRepository repository;

    public PatchRecipeCommand(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Recipe execute(PatchRecipeParameters request) {
        repository.validateExistsById(request.id());
        Recipe recipe = repository.getById(request.id());

        if (request.name() != null && !recipe.getName().equalsIgnoreCase(request.name())) {
            repository.validateNameUnique(request.name().trim().toLowerCase());
            recipe.setName(request.name().trim().toLowerCase());
        }

        if (request.cookTime() != null) {
            recipe.setCookTime(request.cookTime());
        }

        if (request.instructions() != null) {
            request.instructions().stream()
                    .forEach(pi -> patchInstruction(recipe, pi));
        }

        if (request.ingredients() != null) {
            request.ingredients().stream()
                    .forEach(pi -> patchIngredient(recipe, pi));
        }

        repository.save(recipe);
        return recipe;
    }

    private void patchInstruction(Recipe recipe, PatchInstructionParameters parameters) {
        if (parameters.id() != null) {
            if (!recipe.hasInstruction(parameters.id())) {
                throw new RecipeInstructionNotFoundException(parameters.id());
            }
            RecipeInstruction instructionToPatch = recipe.getInstruction(parameters.id());
            if (parameters.instruction() != null) {
                instructionToPatch.setInstruction(parameters.instruction());
            }
            if (parameters.instructionNumber() != null) {
                instructionToPatch.setInstructionNumber(parameters.instructionNumber());
            }
        } else if (parameters.canAddInstruction()) {
            recipe.addInstruction(new RecipeInstruction(
                    repository.nextRecipeInstructionId(),
                    parameters.instructionNumber(),
                    parameters.instruction()));
        } else {
            throw new EntityNotSuitableForUpdateException("RecipeInstruction", "Missing instruction information.");
        }
    }

    private void patchIngredient(Recipe recipe, PatchIngredientParameters parameters) {
        if (parameters.id() != null) {
            if (!recipe.hasIngredient(parameters.id())) {
                throw new RecipeIngredientNotFoundException(parameters.id());
            }
            RecipeIngredient ingredientToPatch = recipe.getIngredient(parameters.id());
            if (parameters.weight() != null) {
                ingredientToPatch.setWeight(parameters.weight());
            }
            if (parameters.ingredientName() != null) {
                ingredientToPatch.setIngredientName(parameters.ingredientName().trim().toLowerCase());
            }
            if (parameters.ingredientType() != null) {
                ingredientToPatch.setIngredientType(parameters.ingredientType());
            }

        } else if (parameters.canAddIngredient()) {
            recipe.addIngredient(new RecipeIngredient(
                    repository.nextRecipeIngredientId(),
                    parameters.ingredientName().trim().toLowerCase(),
                    parameters.ingredientType(),
                    parameters.weight()));
        } else {
            throw new EntityNotSuitableForUpdateException("RecipeIngredient", "Missing ingredient information.");
        }
    }
}
