package org.n3gd0r.infrastructure.web;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.n3gd0r.recipe.domain.exception.EntityNotSuitableForUpdateException;
import org.n3gd0r.recipe.domain.exception.RecipeIngredientNotFoundException;
import org.n3gd0r.recipe.domain.exception.RecipeInstructionNotFoundException;
import org.n3gd0r.recipe.domain.exception.RecipeNameIsEmptyException;
import org.n3gd0r.recipe.domain.exception.RecipeNotFoundException;
import org.n3gd0r.recipe.domain.exception.RecipeWithNameAlreadyExistsException;
import org.n3gd0r.recipe.usecase.exception.NothingToPatchException;
import org.n3gd0r.roulette.domain.exception.NoRecipesFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * Global exception handler for consistent error responses and logging.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleRecipeNotFound(RecipeNotFoundException ex) {
        log.warn("Recipe not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(NoRecipesFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoRecipesFound(NoRecipesFoundException ex) {
        log.warn("No recipes found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(RecipeIngredientNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleRecipeIngredientNotFound(RecipeIngredientNotFoundException ex) {
        log.warn("Recipe ingredient not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(RecipeInstructionNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleRecipeInstructionNotFound(RecipeInstructionNotFoundException ex) {
        log.warn("Recipe instruction not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(RecipeWithNameAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleRecipeNameConflict(RecipeWithNameAlreadyExistsException ex) {
        log.warn("Recipe name conflict: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(RecipeNameIsEmptyException.class)
    public ResponseEntity<Map<String, String>> handleRecipeNameEmpty(RecipeNameIsEmptyException ex) {
        log.warn("Recipe name is empty: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(NothingToPatchException.class)
    public ResponseEntity<Map<String, String>> handleNothingToPatch(NothingToPatchException ex) {
        log.warn("Nothing to patch: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(EntityNotSuitableForUpdateException.class)
    public ResponseEntity<Map<String, String>> handleEntityNotSuitable(EntityNotSuitableForUpdateException ex) {
        log.warn("Entity not suitable for update: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        log.warn("Validation failed: {}", ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "Validation failed", "details", errors.toString()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoResourceFound(NoResourceFoundException ex) {
        log.debug("Static resource not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Resource not found"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneric(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "An unexpected error occurred"));
    }
}
