package org.n3gd0r.recipe.domain;

import org.n3gd0r.commons.AbstractEntityId;

import java.util.UUID;

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
