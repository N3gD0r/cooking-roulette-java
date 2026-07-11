package org.n3gd0r.recipe.web;

import jakarta.validation.Valid;
import org.n3gd0r.infrastructure.mediator.Mediator;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeId;
import org.n3gd0r.recipe.usecase.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * RecipeController
 */
@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    private final Mediator mediator;

    public RecipeController(Mediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecipeResponse registerRecipe(@Valid @RequestBody RegisterRecipeWithAllRequest request) {
        RegisterRecipeWithAllParameters parameters = request.toParameters();
        Recipe recipe = mediator.send(new RegisterRecipeCommand(parameters));
        return RecipeResponse.of(recipe);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecipeResponse getRecipe(@PathVariable UUID id) {
        GetRecipeQuery query = new GetRecipeQuery(new RecipeId(id));
        Recipe recipe = mediator.send(query);
        return RecipeResponse.of(recipe);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public RecipeResponse getRecipeByName(@RequestParam String name) {
        GetRecipeQuery query = new GetRecipeQuery(name);
        Recipe recipe = mediator.send(query);
        return RecipeResponse.of(recipe);
    }

    @GetMapping("/{page}/{size}")
    @ResponseStatus(HttpStatus.OK)
    public List<RecipeResponse> getRecipes(@PathVariable int page, @PathVariable int size) {
        GetAllRecipesQuery query = new GetAllRecipesQuery(new GetAllRecipesParameters(page, size));
        return mediator.send(query).stream().map(RecipeResponse::of).toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Void> deleteRecipe(@PathVariable UUID id) {
        DeleteRecipeCommand deleteCommand = new DeleteRecipeCommand(new DeleteRecipeParameters(id));
        mediator.send(deleteCommand);
        return ResponseEntity.accepted().build();
    }
}
