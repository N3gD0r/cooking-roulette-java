package org.n3gd0r.recipe.web;

import java.util.UUID;

import org.n3gd0r.commons.mediator.IMediator;
import org.n3gd0r.recipe.usecase.delete.DeleteRecipeParameters;
import org.n3gd0r.recipe.usecase.get.GetAllRecipesParameters;
import org.n3gd0r.recipe.usecase.get.GetRecipeParameters;
import org.n3gd0r.recipe.web.dtos.requests.FilterRecipeRequest;
import org.n3gd0r.recipe.web.dtos.requests.PatchRecipeRequest;
import org.n3gd0r.recipe.web.dtos.requests.RegisterRecipeWithAllRequest;
import org.n3gd0r.recipe.web.dtos.requests.UpdateRecipeRequest;
import org.n3gd0r.recipe.web.hateoas.RecipeModelAssembler;
import org.n3gd0r.recipe.web.hateoas.RecipeResponse;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    private final IMediator mediator;
    private final RecipeModelAssembler modelAssembler;
    private final PagedResourcesAssembler<RecipeResponse> pageModelAssembler;

    public RecipeController(IMediator mediator, RecipeModelAssembler assembler,
            PagedResourcesAssembler<RecipeResponse> pageAssembler) {
        this.mediator = mediator;
        this.modelAssembler = assembler;
        this.pageModelAssembler = pageAssembler;
    }

    @PostMapping
    public ResponseEntity<EntityModel<RecipeResponse>> registerRecipe(
            @Valid @RequestBody RegisterRecipeWithAllRequest request) {
        log.info("POST /api/recipes - Registering recipe: {}", request.name());
        var params = request.toParameters();
        var recipe = mediator.send(params);
        var recipeResponse = RecipeResponse.of(recipe);
        log.debug("Recipe registered successfully: {}", recipe.getId());
        var model = modelAssembler.toModel(recipeResponse);
        return ResponseEntity.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(model);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<RecipeResponse>> getRecipe(@PathVariable UUID id) {
        log.info("GET /api/recipes/{} - Getting recipe by id", id);
        var params = new GetRecipeParameters(id);
        var foundRecipe = mediator.send(params);
        var recipeResponse = RecipeResponse.of(foundRecipe);
        log.info("Found recipe: {}", recipeResponse);
        var model = modelAssembler.toModel(recipeResponse);
        return ResponseEntity.ok(model);
    }

    @PostMapping("/search")
    public ResponseEntity<CollectionModel<EntityModel<RecipeResponse>>> getRecipes(
            @RequestBody FilterRecipeRequest request) {
        log.info("GET /api/recipes - Getting all recipes with filters {}", request);
        var query = request.toQuery();
        var recipes = mediator.send(query);
        var recipesResponse = recipes.stream().map(RecipeResponse::of).toList();
        log.info("Got filtered recipes: {}", recipesResponse);
        var collectionModel = modelAssembler.toCollectionModel(recipesResponse);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<RecipeResponse>>> getRecipes(@RequestParam int page,
            @RequestParam int size) {
        log.info("GET /api/recipes - Getting all recipes");
        var query = new GetAllRecipesParameters(page, size);
        var recipes = mediator.send(query);
        var recipesResponse = recipes.map(RecipeResponse::of);
        log.info("Got page of recipes: {}", recipesResponse);
        var pagedModel = pageModelAssembler.toModel(recipesResponse, modelAssembler);
        return ResponseEntity.ok(pagedModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<RecipeResponse>> putRecipe(@PathVariable UUID id,
            @RequestBody UpdateRecipeRequest request) {
        log.info("PUT /api/recipes/{} - Updating recipe", id);
        var params = request.toParameters(id);
        var recipe = mediator.send(params);
        var recipeResponse = RecipeResponse.of(recipe);
        var model = modelAssembler.toModel(recipeResponse);
        return ResponseEntity.ok(model);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EntityModel<RecipeResponse>> patchRecipe(@PathVariable UUID id,
            @RequestBody PatchRecipeRequest request) {
        log.info("PATCH /api/recipes/{} - Patching recipe", id);
        var foundRecipeToPatch = mediator.send(request.toParameters(id));
        var recipeResponse = RecipeResponse.of(foundRecipeToPatch);
        var model = modelAssembler.toModel(recipeResponse);
        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable UUID id) {
        log.info("DELETE /api/recipes/{} - Deleting recipe", id);
        var params = DeleteRecipeParameters.recipeId(id);
        mediator.send(params);
        return ResponseEntity.accepted().build();
    }
}
