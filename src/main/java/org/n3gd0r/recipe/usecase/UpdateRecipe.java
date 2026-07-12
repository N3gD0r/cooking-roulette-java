package org.n3gd0r.recipe.usecase;

import java.lang.reflect.Field;

import org.n3gd0r.infrastructure.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

@Component
@Transactional
public class UpdateRecipe implements RequestHandler<UpdateRecipeCommand, Recipe> {
    private final RecipeRepository repository;

    public UpdateRecipe(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Recipe execute(UpdateRecipeCommand request) {
        repository.validateExistsById(request.id());
        Recipe recipe = repository.getById(request.id());
        request.parameters().remove("id");
        request.parameters().forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Recipe.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, recipe, value);
        });
        repository.save(recipe);
        return recipe;
    }
}
