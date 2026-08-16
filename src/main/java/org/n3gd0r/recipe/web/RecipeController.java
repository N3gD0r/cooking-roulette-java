package org.n3gd0r.recipe.web;

import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.n3gd0r.commons.mediator.IMediator;
import org.n3gd0r.infrastructure.hateoas.DeletedRecipeResponseModel;
import org.n3gd0r.infrastructure.hateoas.RecipeLinkBuilder;
import org.n3gd0r.infrastructure.hateoas.RecipeResponseModel;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeId;
import org.n3gd0r.recipe.usecase.delete.DeleteRecipeParameters;
import org.n3gd0r.recipe.usecase.get.GetAllRecipesParameters;
import org.n3gd0r.recipe.usecase.get.GetRecipeParameters;
import org.n3gd0r.recipe.web.dtos.requests.PatchRecipeRequest;
import org.n3gd0r.recipe.web.dtos.requests.RegisterRecipeWithAllRequest;
import org.n3gd0r.recipe.web.dtos.requests.UpdateRecipeRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * RecipeController
 */
@Slf4j
@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    private final IMediator mediator;

    public RecipeController(IMediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecipeResponseModel registerRecipe(@Valid @RequestBody RegisterRecipeWithAllRequest request) {
        log.info("POST /api/recipes - Registering recipe: {}", request.name());
        Recipe recipe = mediator.send(request.toParameters());
        log.debug("Recipe registered successfully: {}", recipe.getId());
        RecipeResponseModel model = RecipeResponseModel.of(recipe);
        model.add(RecipeLinkBuilder.selfLink(recipe.getId().getId()));
        model.add(RecipeLinkBuilder.collectionLink());
        return model;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecipeResponseModel getRecipe(@PathVariable UUID id) {
        log.info("GET /api/recipes/{} - Getting recipe by id", id);
        Recipe recipe = mediator.send(new GetRecipeParameters(new RecipeId(id), null));
        RecipeResponseModel model = RecipeResponseModel.of(recipe);
        model.add(RecipeLinkBuilder.selfLink(recipe.getId().getId()));
        model.add(RecipeLinkBuilder.collectionLink());
        return model;
    }

    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public RecipeResponseModel getRecipeByName(@RequestParam String name) {
        log.info("GET /api/recipes/filter - Getting recipe by name: {}", name);
        Recipe recipe = mediator.send(new GetRecipeParameters(null, name));
        RecipeResponseModel model = RecipeResponseModel.of(recipe);
        model.add(RecipeLinkBuilder.selfLink(recipe.getId().getId()));
        model.add(RecipeLinkBuilder.collectionLink());
        return model;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<RecipeResponseModel> getRecipes(Pageable pageable) {
        log.info("GET /api/recipes - Getting all recipes with pageable: {}", pageable);
        GetAllRecipesParameters query = new GetAllRecipesParameters(pageable);
        List<RecipeResponseModel> models = mediator.send(query).stream()
                .map(recipe -> {
                    RecipeResponseModel model = RecipeResponseModel.of(recipe);
                    model.add(RecipeLinkBuilder.selfLink(recipe.getId().getId()));
                    return model;
                })
                .toList();
        log.debug("Returning {} recipes", models.size());
        CollectionModel<RecipeResponseModel> collection = CollectionModel.of(models);
        collection.add(RecipeLinkBuilder.collectionLink());
        return collection;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecipeResponseModel patchRecipe(@PathVariable UUID id, @RequestBody PatchRecipeRequest request) {
        log.info("PATCH /api/recipes/{} - Patching recipe", id);
        Recipe recipe = mediator.send(request.toParameters(id));
        RecipeResponseModel model = RecipeResponseModel.of(recipe);
        model.add(RecipeLinkBuilder.selfLink(recipe.getId().getId()));
        model.add(RecipeLinkBuilder.collectionLink());
        return model;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecipeResponseModel putRecipe(@PathVariable UUID id, @RequestBody UpdateRecipeRequest request) {
        log.info("PUT /api/recipes/{} - Updating recipe", id);
        Recipe recipe = mediator.send(request.toParameters(id));
        RecipeResponseModel model = RecipeResponseModel.of(recipe);
        model.add(RecipeLinkBuilder.selfLink(recipe.getId().getId()));
        model.add(RecipeLinkBuilder.collectionLink());
        return model;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DeletedRecipeResponseModel deleteRecipe(@PathVariable UUID id) {
        log.info("DELETE /api/recipes/{} - Deleting recipe", id);
        var wasDeleted = mediator.send(new DeleteRecipeParameters(new RecipeId(id)));
        DeletedRecipeResponseModel model = DeletedRecipeResponseModel.of(wasDeleted == null);
        model.add(RecipeLinkBuilder.collectionLink());
        return model;
    }
}
