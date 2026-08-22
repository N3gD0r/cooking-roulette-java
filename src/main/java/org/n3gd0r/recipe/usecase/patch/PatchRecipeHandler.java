package org.n3gd0r.recipe.usecase.patch;

import org.n3gd0r.commons.mediator.HandlerFor;
import org.n3gd0r.commons.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeIngredient;
import org.n3gd0r.recipe.domain.RecipeInstruction;
import org.n3gd0r.recipe.domain.exception.EntityNotSuitableForPatchException;
import org.n3gd0r.recipe.domain.exception.RecipeIngredientNotFoundException;
import org.n3gd0r.recipe.domain.exception.RecipeInstructionNotFoundException;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.n3gd0r.recipe.usecase.exception.NothingToPatchException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@HandlerFor(PatchRecipeParameters.class)
@Service
public class PatchRecipeHandler implements RequestHandler<PatchRecipeParameters, Recipe> {
    private final RecipeRepository repository;

    public PatchRecipeHandler(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Recipe execute(PatchRecipeParameters request) {
        log.info("Patching recipe: {}", request.id());
        if (request.nothingToPatch()) {
            log.warn("Nothing to patch for recipe: {}", request.id());
            throw new NothingToPatchException();
        }

        repository.validateExistsById(request.id());
        Recipe recipe = repository.getById(request.id());

        if (request.name() != null && !recipe.getName().equalsIgnoreCase(request.name())) {
            log.debug("Updating recipe name from '{}' to '{}'", recipe.getName(), request.name());
            repository.validateNameUnique(request.name().trim().toLowerCase());
            recipe.setName(request.name().trim().toLowerCase());
        }

        if (request.cookTime() != null) {
            log.debug("Updating cook time to: {}", request.cookTime());
            recipe.setCookTime(request.cookTime());
        }

        if (request.instructions() != null) {
            log.debug("Patching {} instructions for recipe: {}", request.instructions().size(), request.id());
            request.instructions().stream()
                    .forEach(pi -> patchInstruction(recipe, pi));
        }

        if (request.ingredients() != null) {
            log.debug("Patching {} ingredients for recipe: {}", request.ingredients().size(), request.id());
            request.ingredients().stream()
                    .forEach(pi -> patchIngredient(recipe, pi));
        }

        repository.save(recipe);
        log.info("Recipe patched successfully: {}", request.id());
        return recipe;
    }

    private void patchInstruction(Recipe recipe, PatchInstructionParameters parameters) {
        if (parameters.id() != null) {
            if (!recipe.hasInstruction(parameters.id())) {
                log.error("Recipe instruction not found: {}", parameters.id());
                throw new RecipeInstructionNotFoundException(parameters.id());
            }
            RecipeInstruction instructionToPatch = recipe.getInstruction(parameters.id());
            if (parameters.instruction() != null) {
                log.debug("Updating instruction text for: {}", parameters.id());
                instructionToPatch.setInstruction(parameters.instruction());
            }
            if (parameters.instructionNumber() != null) {
                log.debug("Updating instruction number for: {} -> {}", parameters.id(), parameters.instructionNumber());
                instructionToPatch.setInstructionNumber(parameters.instructionNumber());
            }
        } else if (parameters.canAddInstruction()) {
            log.debug("Adding new instruction: {}", parameters.instruction());
            recipe.addInstruction(new RecipeInstruction(
                    repository.nextRecipeInstructionId(),
                    parameters.instructionNumber(),
                    parameters.instruction()));
        } else {
            log.error("Entity not suitable for update: RecipeInstruction - Missing instruction information");
            throw new EntityNotSuitableForPatchException("RecipeInstruction", "Missing instruction information.");
        }
    }

    private void patchIngredient(Recipe recipe, PatchIngredientParameters parameters) {
        if (parameters.id() != null) {
            if (!recipe.hasIngredient(parameters.id())) {
                log.error("Recipe ingredient not found: {}", parameters.id());
                throw new RecipeIngredientNotFoundException(parameters.id());
            }
            RecipeIngredient ingredientToPatch = recipe.getIngredient(parameters.id());
            if (parameters.weight() != null) {
                log.debug("Updating weight for ingredient: {}", parameters.id());
                ingredientToPatch.setWeight(parameters.weight());
            }
            if (parameters.ingredientName() != null) {
                log.debug("Updating ingredient name for: {}", parameters.id());
                ingredientToPatch.setIngredientName(parameters.ingredientName().trim().toLowerCase());
            }
            if (parameters.ingredientType() != null) {
                log.debug("Updating ingredient type for: {}", parameters.id());
                ingredientToPatch.setIngredientType(parameters.ingredientType());
            }

        } else if (parameters.canAddIngredient()) {
            log.debug("Adding new ingredient: {}", parameters.ingredientName());
            recipe.addIngredient(new RecipeIngredient(
                    repository.nextRecipeIngredientId(),
                    parameters.ingredientName().trim().toLowerCase(),
                    parameters.ingredientType(),
                    parameters.weight()));
        } else {
            log.error("Entity not suitable for update: RecipeIngredient - Missing ingredient information");
            throw new EntityNotSuitableForPatchException("RecipeIngredient", "Missing ingredient information.");
        }
    }
}
