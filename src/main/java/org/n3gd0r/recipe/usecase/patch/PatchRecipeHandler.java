package org.n3gd0r.recipe.usecase.patch;

import java.util.UUID;

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
@Service
@HandlerFor(PatchRecipeParameters.class)
public class PatchRecipeHandler implements RequestHandler<PatchRecipeParameters, Recipe> {
    private final RecipeRepository repository;

    public PatchRecipeHandler(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Recipe execute(PatchRecipeParameters request) {
        if (request.nothingToPatch()) {
            log.warn("Nothing to patch for recipe: {}", request.id());
            throw new NothingToPatchException();
        }

        log.info("Patching recipe: {}", request.id());
        repository.validateExistsById(request.id());
        Recipe recipe = repository.getById(request.id());

        patchRecipeFields(recipe, request);
        patchInstructions(recipe, request);
        patchIngredients(recipe, request);

        repository.save(recipe);
        log.info("Recipe patched successfully with id {}", request.id());
        return recipe;
    }

    private void patchRecipeFields(Recipe recipe, PatchRecipeParameters request) {
        request.name().ifPresent((name) -> {
            repository.validateNameUnique(name);
            recipe.setName(name);
        });
        request.cookTime().ifPresent(recipe::setCookTime);
    }

    private void patchIngredients(Recipe recipe, PatchRecipeParameters request) {
        request.ingredients().ifPresent(ingredients -> {
            for (var ingrParams : ingredients) {
                ingrParams.id().ifPresentOrElse(id -> {
                    RecipeIngredient ingredient = findIngredient(recipe, id);
                    applyIngredientUpdates(ingredient, ingrParams);
                }, () -> {
                    if (ingrParams.canAddIngredient()) {
                        recipe.addIngredient(new RecipeIngredient(
                                repository.nextRecipeIngredientId(),
                                ingrParams.ingredientName().get(),
                                ingrParams.ingredientType().get(),
                                Mass.ofGrams(ingrParams.weightInGrams().get())));
                    } else {
                        throw new EntityNotSuitableForPatchException("RecipeIngredient",
                                "Missing required ingredient information.");
                    }
                });
            }
        });
    }

    private void patchInstructions(Recipe recipe, PatchRecipeParameters request) {
        request.instructions().ifPresent(instructions -> {
            for (var instrParams : instructions) {
                instrParams.id().ifPresentOrElse(id -> {
                    RecipeInstruction instruction = findInstruction(recipe, id);
                    applyInstructionUpdates(instruction, instrParams);
                }, () -> {
                    if (instrParams.canAddInstruction()) {
                        recipe.addInstruction(new RecipeInstruction(
                                repository.nextRecipeInstructionId(),
                                instrParams.instructionNumber().get(),
                                instrParams.instruction().get()));
                    } else {
                        throw new EntityNotSuitableForPatchException("RecipeInstruction",
                                "Missing required recipe instruction information.");
                    }
                });
            }
        });
    }

    private void applyIngredientUpdates(RecipeIngredient ingredient, PatchIngredientParameters ingredientParameters) {
        ingredientParameters.weightInGrams()
                .ifPresent(weightInGrams -> ingredient.setWeight(Mass.ofGrams(weightInGrams)));
        ingredientParameters.ingredientName().ifPresent(ingredient::setIngredientName);
        ingredientParameters.ingredientType().ifPresent(ingredient::setIngredientType);
    }

    private void applyInstructionUpdates(RecipeInstruction instruction,
            PatchInstructionParameters instructionParameters) {
        instructionParameters.instruction().ifPresent(instruction::setInstruction);
        instructionParameters.instructionNumber().ifPresent(instruction::setInstructionNumber);
    }

    private RecipeInstruction findInstruction(Recipe recipe, UUID id) {
        if (!recipe.hasInstruction(id)) {
            log.debug("Instruction not found for patching: {}", id);
            throw new RecipeInstructionNotFoundException(id);
        }
        return recipe.getInstruction(id);
    }

    private RecipeIngredient findIngredient(Recipe recipe, UUID id) {
        if (!recipe.hasIngredient(id)) {
            log.debug("Ingredient not found for patching: {}", id);
            throw new RecipeIngredientNotFoundException(id);
        }
        return recipe.getIngredient(id);
    }
}
