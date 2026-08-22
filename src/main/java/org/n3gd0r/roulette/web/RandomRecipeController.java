package org.n3gd0r.roulette.web;

import org.n3gd0r.commons.mediator.IMediator;
import org.n3gd0r.infrastructure.hateoas.RecipeModelAssembler;
import org.n3gd0r.infrastructure.hateoas.RecipeResponse;
import org.n3gd0r.roulette.usecase.RandomRecipeParameters;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/recipes")
public class RandomRecipeController {
    private final IMediator mediator;
    private final RecipeModelAssembler modelAssembler;

    public RandomRecipeController(IMediator mediator, RecipeModelAssembler modelAssembler) {
        this.mediator = mediator;
        this.modelAssembler = modelAssembler;
    }

    @GetMapping("/random")
    public ResponseEntity<EntityModel<RecipeResponse>> getRandomRecipe() {
        log.info("GET /api/random/recipe - Getting random recipe");
        var params = new RandomRecipeParameters();
        var recipe = mediator.send(params);
        var recipeResponse = RecipeResponse.of(recipe);
        var model = modelAssembler.toModel(recipeResponse);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/random/filter")
    public ResponseEntity<EntityModel<RecipeResponse>> getRandomRecipe(@RequestParam int pageSize,
            RandomRecipeRequest request) {
        log.info("GET /api/recipes/random/filter?pageSize={} - Getting random recipe using page size: {}", pageSize,
                pageSize);
        log.info("Using filters: {}", request);
        var params = request.toQuery(pageSize);
        var recipe = mediator.send(params);
        var recipeResponse = RecipeResponse.of(recipe);
        var model = modelAssembler.toModel(recipeResponse);
        return ResponseEntity.ok(model);
    }
}
