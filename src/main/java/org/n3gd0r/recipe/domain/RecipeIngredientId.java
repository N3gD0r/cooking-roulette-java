package org.n3gd0r.recipe.domain;

import org.n3gd0r.commons.AbstractEntityId;

import java.util.UUID;

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
