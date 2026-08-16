package org.n3gd0r.roulette.web;

import lombok.extern.slf4j.Slf4j;

import org.n3gd0r.commons.mediator.IMediator;
import org.n3gd0r.infrastructure.hateoas.RecipeLinkBuilder;
import org.n3gd0r.infrastructure.hateoas.RecipeResponseModel;
import org.n3gd0r.roulette.usecase.RandomRecipeParameters;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/random/recipe")
public class RandomRecipeController {
    private final IMediator mediator;

    public RandomRecipeController(IMediator mediator) {
        this.mediator = mediator;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public RecipeResponseModel getRandomRecipe() {
        log.info("GET /api/random/recipe - Getting random recipe");
        RecipeResponseModel model = RecipeResponseModel.of(mediator.send(new RandomRecipeParameters()));
        model.add(RecipeLinkBuilder.selfLink(model.getId()));
        model.add(RecipeLinkBuilder.collectionLink());
        model.add(RecipeLinkBuilder.randomLink());
        return model;
    }

    @PostMapping("/{pageSize}")
    @ResponseStatus(HttpStatus.OK)
    public RecipeResponseModel getRandomRecipe(@PathVariable int pageSize, @RequestBody RandomRecipeRequest params) {
        log.info("POST /api/random/recipe/{} - Getting random recipe with filters", pageSize);
        RecipeResponseModel model = RecipeResponseModel.of(mediator.send(params.toQuery(pageSize)));
        model.add(RecipeLinkBuilder.selfLink(model.getId()));
        model.add(RecipeLinkBuilder.collectionLink());
        model.add(RecipeLinkBuilder.randomLink());
        return model;
    }
}
