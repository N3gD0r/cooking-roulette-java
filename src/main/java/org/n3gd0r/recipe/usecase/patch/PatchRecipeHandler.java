package org.n3gd0r.recipe.usecase.patch;

import org.n3gd0r.commons.mediator.HandlerFor;
import org.n3gd0r.commons.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.Mass;
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
            log.debug("Patching {} instructions for recipe with id {}", request.instructions().size(), request.id());
            request.instructions().stream()
                    .forEach(pi -> patchInstruction(recipe, pi));
        }

        if (request.ingredients() != null) {
            log.debug("Patching {} ingredients for recipe with id {}", request.ingredients().size(), request.id());
            request.ingredients().stream()
                    .forEach(pi -> patchIngredient(recipe, pi));
        }

        repository.save(recipe);
        log.info("Recipe patched successfully with id {}", request.id());
        return recipe;
    }

    private void patchInstruction(Recipe recipe, PatchInstructionParameters instructionParameters) {
        if (instructionParameters.id() != null) {
            if (!recipe.hasInstruction(instructionParameters.id())) {
                log.error("Recipe instruction not found for patching with id {}", instructionParameters.id());
                throw new RecipeInstructionNotFoundException(instructionParameters.id());
            }

            RecipeInstruction instructionToPatch = recipe.getInstruction(instructionParameters.id());

            if (instructionParameters.instruction() != null) {
                log.debug("Updating instruction text for recipe instruction with id {}", instructionParameters.id());
                instructionToPatch.setInstruction(instructionParameters.instruction());
            }
            if (instructionParameters.instructionNumber() != null) {
                log.debug("Updating instruction number for recipe instruction with id {} and instruction number {}",
                        instructionParameters.id(),
                        instructionParameters.instructionNumber());
                instructionToPatch.setInstructionNumber(instructionParameters.instructionNumber());
            }
        } else if (instructionParameters.canAddInstruction()) {
            log.debug("No id provided, adding new recipe instruction: {}", instructionParameters.instruction());
            recipe.addInstruction(new RecipeInstruction(repository.nextRecipeInstructionId(),
                    instructionParameters.instructionNumber(),
                    instructionParameters.instruction()));
        } else {
            log.error("Entity not suitable for update: RecipeInstruction - Missing instruction information");
            throw new EntityNotSuitableForPatchException("RecipeInstruction", "Missing instruction information.");
        }
    }

    private void patchIngredient(Recipe recipe, PatchIngredientParameters parameters) {
        if (parameters.id() != null) {
            if (!recipe.hasIngredient(parameters.id())) {
                log.error("Recipe ingredient not found with id {}", parameters.id());
                throw new RecipeIngredientNotFoundException(parameters.id());
            }
            RecipeIngredient ingredientToPatch = recipe.getIngredient(parameters.id());
            if (parameters.weightInGrams() != null) {
                log.debug("Updating weight for recipe ingredient with id {} and weight {}", parameters.id(),
                        parameters.weightInGrams());
                ingredientToPatch.setWeight(Mass.ofGrams(parameters.weightInGrams()));
            }
            if (parameters.ingredientName() != null) {
                log.debug("Updating ingredient name for recipe ingredient with id {} and name {}", parameters.id(),
                        parameters.ingredientName());
                ingredientToPatch.setIngredientName(parameters.ingredientName().trim().toLowerCase());
            }
            if (parameters.ingredientType() != null) {
                log.debug("Updating ingredient type for recipe ingredient with id {} and ingredient type {}",
                        parameters.id(), parameters.ingredientType());
                ingredientToPatch.setIngredientType(parameters.ingredientType());
            }

        } else if (parameters.canAddIngredient()) {
            log.debug("No recipe ingredient provided, adding new ingredient: {}", parameters.ingredientName());
            recipe.addIngredient(new RecipeIngredient(repository.nextRecipeIngredientId(),
                    parameters.ingredientName().trim().toLowerCase(),
                    parameters.ingredientType(),
                    Mass.ofGrams(parameters.weightInGrams())));
        } else {
            log.error("Entity not suitable for update: RecipeIngredient - Missing ingredient information");
            throw new EntityNotSuitableForPatchException("RecipeIngredient", "Missing ingredient information.");
        }
    }
}
