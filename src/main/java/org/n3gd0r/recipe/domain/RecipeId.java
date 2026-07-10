package org.n3gd0r.recipe.domain;

import java.util.UUID;

import org.n3gd0r.commons.AbstractEntityId;

/**
 * RecipeId
 */
public class RecipeId extends AbstractEntityId<UUID> {
    protected RecipeId() {

    }

    public RecipeId(UUID id) {
        super(id);
    }
}
