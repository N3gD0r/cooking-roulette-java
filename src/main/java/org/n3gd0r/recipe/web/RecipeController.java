package org.n3gd0r.recipe.web;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.n3gd0r.infrastructure.mediator.Mediator;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeId;
import org.n3gd0r.recipe.usecase.DeleteRecipeCommand;
import org.n3gd0r.recipe.usecase.GetAllRecipesQuery;
import org.n3gd0r.recipe.usecase.GetRecipeQuery;
import org.n3gd0r.recipe.usecase.RegisterRecipeCommand;
import org.n3gd0r.recipe.usecase.UpdateRecipeCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

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
        Recipe recipe = mediator.send(new RegisterRecipeCommand(request.toParameters()));
        return RecipeResponse.of(recipe);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecipeResponse getRecipe(@PathVariable UUID id) {
        Recipe recipe = mediator.send(new GetRecipeQuery(new RecipeId(id)));
        return RecipeResponse.of(recipe);
    }

    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public RecipeResponse getRecipeByName(@RequestParam String name) {
        Recipe recipe = mediator.send(new GetRecipeQuery(name));
        return RecipeResponse.of(recipe);
    }

    @GetMapping("/{page}/{size}")
    @ResponseStatus(HttpStatus.OK)
    public List<RecipeResponse> getRecipes(@PathVariable(required = false) int page,
            @PathVariable(required = false) int size) {
        GetAllRecipesQuery query = new GetAllRecipesQuery(page, size);
        return mediator.send(query).stream().map(RecipeResponse::of).toList();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RecipeResponse> getRecipes() {
        return mediator.send(new GetAllRecipesQuery()).stream().map(RecipeResponse::of).toList();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecipeResponse patchRecipe(@PathVariable UUID id, @RequestBody Map<String, Object> patchFields) {
        Recipe recipe = mediator.send(new UpdateRecipeCommand(new RecipeId(id), patchFields));
        return RecipeResponse.of(recipe);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Void> deleteRecipe(@PathVariable UUID id) {
        DeleteRecipeCommand deleteCommand = new DeleteRecipeCommand(new RecipeId(id));
        mediator.send(deleteCommand);
        return ResponseEntity.accepted().build();
    }
}
