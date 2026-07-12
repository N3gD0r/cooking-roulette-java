package org.n3gd0r.recipe.usecase;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import org.n3gd0r.commons.AbstractEntity;
import org.n3gd0r.commons.AbstractEntityId;
import org.n3gd0r.infrastructure.mediator.RequestHandler;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeIngredient;
import org.n3gd0r.recipe.domain.RecipeIngredientId;
import org.n3gd0r.recipe.domain.RecipeStep;
import org.n3gd0r.recipe.domain.RecipeStepId;
import org.n3gd0r.recipe.domain.exception.EntityNotSuitableForUpdateException;
import org.n3gd0r.recipe.domain.exception.RecipeIngredientNotFoundException;
import org.n3gd0r.recipe.domain.exception.RecipeStepNotFoundException;
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

    @SuppressWarnings("unchecked")
    @Override
    public Recipe execute(UpdateRecipeCommand request) {
        repository.validateExistsById(request.id());
        Recipe recipe = repository.getById(request.id());
        Map<String, Object> parameters = request.parameters();
        List<Map<String, Object>> instructionsPatches = (List<Map<String, Object>>) parameters.remove("instructions");
        List<Map<String, Object>> ingredientsPatches = (List<Map<String, Object>>) parameters.remove("ingredients");

        if (parameters.containsKey("id")) {
            parameters.remove("id");
        }

        if (instructionsPatches != null) {
            patchCollection(instructionsPatches,
                    recipe.getInstructions(),
                    RecipeStep.class,
                    RecipeStepId::new,
                    RecipeStepNotFoundException::new);
        }

        if (ingredientsPatches != null) {
            patchCollection(ingredientsPatches,
                    recipe.getIngredients(),
                    RecipeIngredient.class,
                    RecipeIngredientId::new,
                    RecipeIngredientNotFoundException::new);

        }

        setFields(parameters, recipe, Recipe.class);

        repository.save(recipe);
        return recipe;
    }

    private <T extends AbstractEntity<ID>, ID extends AbstractEntityId<UUID>> void patchCollection(
            List<Map<String, Object>> patches,
            List<T> entities,
            Class<T> clazz,
            Function<UUID, ID> idFactory,
            Function<ID, ? extends RuntimeException> notFoundException) {
        for (Map<String, Object> patch : patches) {
            ID id = idFactory.apply(extractId(patch, clazz));
            T entity = entities.stream()
                    .filter(e -> e.getId().equals(id))
                    .findAny()
                    .orElseThrow(() -> notFoundException.apply(id));
            setFields(patch, entity, clazz);
        }
    }

    private <T> void setFields(Map<String, Object> patch, T entity, Class<T> clazz) {
        patch.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(clazz, key);
            if (field == null) {
                throw new IllegalArgumentException("Unknown field %s for %s".formatted(key, clazz.getSimpleName()));
            }
            field.setAccessible(true);
            ReflectionUtils.setField(field, entity, value);
        });
    }

    private UUID extractId(Map<String, Object> patch, Class<?> clazz) {
        Object rawId = patch.remove("id");
        if (rawId == null) {
            throw new EntityNotSuitableForUpdateException(clazz.getSimpleName());
        }
        return UUID.fromString(rawId.toString());
    }
}
