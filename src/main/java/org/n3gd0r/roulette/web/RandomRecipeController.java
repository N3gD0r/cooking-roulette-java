package org.n3gd0r.roulette.web;

import org.n3gd0r.infrastructure.mediator.Mediator;
import org.n3gd0r.recipe.web.dtos.responses.RecipeResponse;
import org.n3gd0r.roulette.usecase.RandomRecipeParameters;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/random/recipe")
public class RandomRecipeController {
    private final Mediator mediator;

    public RandomRecipeController(Mediator mediator) {
        this.mediator = mediator;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public RecipeResponse getRandomRecipe() {
        return RecipeResponse.of(mediator.send(new RandomRecipeParameters()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public RecipeResponse getRandomRecipe(@RequestBody RandomRecipeRequest params) {
        return RecipeResponse.of(mediator.send(params.toQuery()));
    }
}
