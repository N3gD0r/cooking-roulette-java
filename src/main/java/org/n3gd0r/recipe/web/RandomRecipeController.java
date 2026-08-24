package org.n3gd0r.recipe.web;

import org.n3gd0r.commons.mediator.IMediator;
import org.n3gd0r.recipe.usecase.random.RandomRecipeParameters;
import org.n3gd0r.recipe.web.dtos.requests.RandomRecipeRequest;
import org.n3gd0r.recipe.web.hateoas.RecipeModelAssembler;
import org.n3gd0r.recipe.web.hateoas.RecipeResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
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

    @PostMapping("/random/match")
    public ResponseEntity<EntityModel<RecipeResponse>> getRandomRecipe(@RequestBody RandomRecipeRequest request) {
        log.info("GET /api/random/match - using filters: {}", request);
        var params = request.toRandomQuery();
        if (params.isEmptyRequest()) {
            log.info("GET /api/random/match - no filters provided, switching to GET /api/random/recipe");
            log.debug("Calling getRandomRecipe() with no filtering");
            return getRandomRecipe();
        }
        var recipe = mediator.send(params);
        var recipeResponse = RecipeResponse.of(recipe);
        var model = modelAssembler.toModel(recipeResponse);
        return ResponseEntity.ok(model);
    }
}
