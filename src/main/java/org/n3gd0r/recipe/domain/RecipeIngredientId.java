package org.n3gd0r.recipe.domain;

import java.util.UUID;

import org.n3gd0r.commons.AbstractEntityId;

/**
 * RecipeIngredientId
 */
public class RecipeIngredientId extends AbstractEntityId<UUID> {
    protected RecipeIngredientId() {
    }

    public RecipeIngredientId(UUID id) {
        super(id);
    }
}
