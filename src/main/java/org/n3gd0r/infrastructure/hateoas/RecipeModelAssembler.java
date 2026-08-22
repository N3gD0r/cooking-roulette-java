package org.n3gd0r.infrastructure.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.n3gd0r.recipe.web.RecipeController;
import org.n3gd0r.roulette.web.RandomRecipeController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class RecipeModelAssembler implements RepresentationModelAssembler<RecipeResponse, EntityModel<RecipeResponse>> {

    @Override
    public EntityModel<RecipeResponse> toModel(RecipeResponse recipeResponse) {
        return EntityModel.of(recipeResponse,
                linkTo(methodOn(RecipeController.class).getRecipe(recipeResponse.id())).withSelfRel(),
                linkTo(methodOn(RecipeController.class).getRecipeByName(recipeResponse.name())).withRel("self_by_name"),
                linkTo(methodOn(RandomRecipeController.class).getRandomRecipe()).withRel("random"),
                linkTo(methodOn(RecipeController.class).getRecipes(0, 20)).withRel(IanaLinkRelations.COLLECTION));
    }
}
