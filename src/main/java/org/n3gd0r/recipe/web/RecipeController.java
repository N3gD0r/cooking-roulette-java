package org.n3gd0r.recipe.web;

import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.usecase.RegisterRecipe;
import org.n3gd0r.recipe.usecase.RegisterRecipeWithAllParameters;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * RecipeController
 */
@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    private final RegisterRecipe registerRecipe;

    public RecipeController(RegisterRecipe registerRecipe) {
        this.registerRecipe = registerRecipe;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecipeResponse registerRecipe(@Valid @RequestBody RegisterRecipeWithAllRequest request) {
        RegisterRecipeWithAllParameters parameters = request.toParameters();
        Recipe recipe = registerRecipe.execute(parameters);
        return RecipeResponse.of(recipe);
    }
}
