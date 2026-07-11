package org.n3gd0r.recipe.domain;

import org.n3gd0r.commons.AbstractEntityId;

import java.util.UUID;

/**
 * RecipeStepId
 */
public class RecipeStepId extends AbstractEntityId<UUID> {
    protected RecipeStepId() {
    }

    public RecipeStepId(UUID id) {
        super(id);
    }
}
