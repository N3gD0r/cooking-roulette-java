package org.n3gd0r.recipe.domain;

import java.util.UUID;

import org.n3gd0r.commons.AbstractEntityId;

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
