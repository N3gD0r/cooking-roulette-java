package org.n3gd0r.recipe.web.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.n3gd0r.recipe.web.RandomRecipeController;
import org.n3gd0r.recipe.web.RecipeController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class RecipeModelAssembler implements RepresentationModelAssembler<RecipeResponse, EntityModel<RecipeResponse>> {

    @Override
    public EntityModel<RecipeResponse> toModel(RecipeResponse recipeResponse) {
        return EntityModel.of(recipeResponse,
                linkTo(methodOn(RecipeController.class).getRecipe(recipeResponse.id())).withSelfRel(),
                linkTo(methodOn(RandomRecipeController.class).getRandomRecipe()).withRel("random"));
    }
}
